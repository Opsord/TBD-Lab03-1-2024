package G1TBD.LABTBD.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostgisVerificator implements InitializingBean {

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
}
