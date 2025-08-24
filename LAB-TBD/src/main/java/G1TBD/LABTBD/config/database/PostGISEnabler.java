// src/main/java/G1TBD/LABTBD/config/PostGISEnabler.java
package G1TBD.LABTBD.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import java.nio.charset.StandardCharsets;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostGISEnabler implements InitializingBean {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Override
    public void afterPropertiesSet() throws Exception {
        verifyPostGIS();
        initializeSchema();
    }

    private void verifyPostGIS() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            System.out.println("Verificando disponibilidad de PostGIS...");

            Boolean isInstalled = jdbcTemplate.queryForObject(
                    "SELECT EXISTS(SELECT 1 FROM pg_extension WHERE extname = 'postgis')", Boolean.class
            );

            if (!Boolean.TRUE.equals(isInstalled)) {
                System.err.println("ERROR: PostGIS no está instalado en la base de datos.");
                System.err.println("Por favor, instale PostGIS ejecutando: CREATE EXTENSION postgis;");
                System.err.println("La aplicación se cerrará.");
                System.exit(1);
            } else {
                System.out.println("✓ PostGIS está disponible y activo.");
            }
        } catch (Exception e) {
            System.err.println("ERROR: No se pudo verificar PostGIS: " + e.getMessage());
            System.err.println("Verifique que:");
            System.err.println("1. PostgreSQL esté ejecutándose");
            System.err.println("2. La base de datos exista");
            System.err.println("3. Las credenciales sean correctas");
            System.err.println("La aplicación se cerrará.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void initializeSchema() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            System.out.println("Inicializando schema de base de datos...");

            // Read schema.sql file
            ClassPathResource resource = new ClassPathResource("schema.sql");
            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String schemaScript = new String(bdata, StandardCharsets.UTF_8);

            // Split the script properly, considering PostgreSQL function delimiters
            String[] statements = splitSqlStatements(schemaScript);

            for (String statement : statements) {
                String trimmedStatement = statement.trim();
                if (!trimmedStatement.isEmpty() && !trimmedStatement.startsWith("--")) {
                    String s = trimmedStatement.length() > 50 ?
                            trimmedStatement.substring(0, 50) + "..." : trimmedStatement;
                    try {
                        jdbcTemplate.execute(trimmedStatement);
                        System.out.println("✓ Ejecutado: " + s);
                    } catch (Exception e) {
                        // Check if it's just a "function already exists" error, which is OK
                        if (e.getMessage().contains("already exists") ||
                                e.getMessage().contains("ya existe")) {
                            System.out.println("⚠ Función/trigger ya existe: " + s);
                        } else {
                            System.err.println("Error ejecutando statement: " + trimmedStatement.substring(0, Math.min(100, trimmedStatement.length())));
                            throw e;
                        }
                    }
                }
            }

            System.out.println("✓ Schema inicializado correctamente.");

        } catch (Exception e) {
            System.err.println("ERROR: No se pudo inicializar el schema: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String[] splitSqlStatements(String script) {
        java.util.List<String> statements = new java.util.ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        boolean inFunction = false;
        String[] lines = script.split("\n");

        for (String line : lines) {
            String trimmedLine = line.trim();

            // Skip comments and empty lines
            if (trimmedLine.startsWith("--") || trimmedLine.isEmpty()) {
                continue;
            }

            currentStatement.append(line).append("\n");

            // Check if we're entering a function (starting with $$)
            if (trimmedLine.contains("$$") && !inFunction) {
                inFunction = true;
            }
            // Check if we're exiting a function (ending with $$)
            else if (trimmedLine.contains("$$") && inFunction) {
                inFunction = false;
                // End of function, add the complete statement
                statements.add(currentStatement.toString().trim());
                currentStatement = new StringBuilder();
            }
            // If not in function and line ends with semicolon, it's a complete statement
            else if (!inFunction && trimmedLine.endsWith(";")) {
                statements.add(currentStatement.toString().trim());
                currentStatement = new StringBuilder();
            }
        }

        // Add any remaining statement
        if (!currentStatement.isEmpty()) {
            String remaining = currentStatement.toString().trim();
            if (!remaining.isEmpty()) {
                statements.add(remaining);
            }
        }

        return statements.toArray(new String[0]);
    }
}