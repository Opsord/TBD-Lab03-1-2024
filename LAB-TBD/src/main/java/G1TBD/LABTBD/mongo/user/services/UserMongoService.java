package G1TBD.LABTBD.mongo.user.services;

import G1TBD.LABTBD.app.emergency.services.EmergencyService;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.models.UserRole;
import G1TBD.LABTBD.mongo.user.models.UserSkill;
import G1TBD.LABTBD.mongo.user.repositories.UserMongoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserMongoService {

    private static final Logger logger = Logger.getLogger(UserMongoService.class.getName());

    private final SkillService skillService;
    private final PointService pointService;
    private final EmergencyService emergencyService;
    private final UserMongoRepository userMongoRepository;

    public UserMongoService(SkillService skillService , UserMongoRepository userMongoRepository, PointService pointService, EmergencyService emergencyService) {
        this.userMongoRepository = userMongoRepository;
        this.pointService = pointService;
        this.skillService = skillService;
        this.emergencyService = emergencyService;
    }

    //--------------------------CREATE--------------------------

    public void saveUser(UserMongo user) {
        logger.info("Create from UserMongoService");
        if (user.getSkills() != null) {
            List<UserSkill> processedSkills = new ArrayList<>();
            for (UserSkill skill : user.getSkills()) {
                UserSkill existingSkill = skillService.getSkillBySkillName(skill.getName());
                if (existingSkill == null) {
                    existingSkill = skillService.saveSkill(skill);
                }
                processedSkills.add(existingSkill);
            }
            user.setSkills(processedSkills);
        }
        logger.info("User skills processed");
        userMongoRepository.save(user);
    }

    //---------------------------READ---------------------------

    public List<UserMongo> getUsers(){
        return userMongoRepository.findAll();
    }

    public Optional<UserMongo> getUserById(String id){
        return userMongoRepository.findById(id);
    }

    public Optional<UserMongo> getUserByRut(String rut){
        return userMongoRepository.findUserMongoByRut(rut);
    }

    public Optional<UserMongo> getUserByEmail(String email){
        return userMongoRepository.findUserMongoByEmail(email);
    }


    public List<UserMongo> getXNearbyVolunteers(Long emergency_id, double radius, int quantity){
        //Listado de los ruts de los usuarios (voluntarios y coordinadores) que están cerca
        List<String> NearbyVolunteersRut = emergencyService.getXNearbyVolunteers(emergency_id, radius, quantity);
        List<UserMongo> volunteers = new ArrayList<>();

        for(String rut: NearbyVolunteersRut){
            UserMongo user = userMongoRepository.findUserMongoByRut(rut).orElse(null);

            // Verificar que el usuario sea voluntario y esté disponible
            if(user != null && user.isAvailability() && user.getRole().equals(UserRole.VOLUNTARIO)){
                volunteers.add(user);
            }
        }

        return volunteers;
    }

    //Funcionalidad laboratorio 3
    public double obtenerPromedioHabilidades() {
        long totalSkills = 0;
        long totalVolunteers = 0;

        List<UserMongo> allVolunteers =
                userMongoRepository.findAll();

        for (UserMongo user : allVolunteers) {
            if (String.valueOf(user.getRole()).equals("VOLUNTARIO")) {
                totalSkills += user.getSkills().size();
                totalVolunteers++;
            }
        }
        
        if (totalVolunteers== 0) {
            return 0;
        } else {
            return (double) totalSkills / totalVolunteers;
        }

    }

    //--------------------------UPDATE--------------------------

    public void updateUser(UserMongo user) {
        if (user.getUser_id() == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo para la actualización.");
        }
        // El método save actúa como un upsert: actualiza si el documento existe, inserta si no.
        userMongoRepository.save(user);
    }

    //--------------------------DELETE--------------------------

    public boolean deleteUser(UserMongo user) throws Exception {
        try {
            userMongoRepository.delete(user);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteUserById(String id) throws Exception {
        try {
            userMongoRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
