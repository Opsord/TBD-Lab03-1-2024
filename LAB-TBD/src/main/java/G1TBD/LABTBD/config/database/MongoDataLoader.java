package G1TBD.LABTBD.config.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.models.UserSkill;
import G1TBD.LABTBD.mongo.user.repositories.UserMongoRepository;
import G1TBD.LABTBD.mongo.user.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Component
public class MongoDataLoader {

    @Autowired
    private UserMongoRepository userRepo;

    @Autowired
    private SkillRepository skillRepo;

    @PostConstruct
    public void loadData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // Register the JavaTimeModule to handle LocalDate and other Java 8 time types
        mapper.registerModule(new JavaTimeModule());

        // Check if data already exists to avoid duplicates
        long userCount = userRepo.count();
        long skillCount = skillRepo.count();

        if (userCount > 0) {
            System.out.println("MongoDB ya contiene " + userCount + " usuarios. Omitiendo carga de datos de usuarios.");
        } else {
            // Load users
            System.out.println("Cargando datos de usuarios en MongoDB...");
            InputStream usersStream = new ClassPathResource("LoadDataUsersMongoDB.json").getInputStream();
            List<UserMongo> users = mapper.readValue(usersStream, new TypeReference<List<UserMongo>>() {});
            userRepo.saveAll(users);
            System.out.println("✓ Cargados " + users.size() + " usuarios en MongoDB.");
        }

        if (skillCount > 0) {
            System.out.println("MongoDB ya contiene " + skillCount + " habilidades. Omitiendo carga de datos de habilidades.");
        } else {
            // Load skills
            System.out.println("Cargando datos de habilidades en MongoDB...");
            InputStream skillsStream = new ClassPathResource("LoadDataSkillsMongoDB.json").getInputStream();
            List<UserSkill> skills = mapper.readValue(skillsStream, new TypeReference<List<UserSkill>>() {});
            skillRepo.saveAll(skills);
            System.out.println("✓ Cargadas " + skills.size() + " habilidades en MongoDB.");
        }
    }
}