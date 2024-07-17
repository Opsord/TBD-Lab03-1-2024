package G1TBD.LABTBD.mongo.user.services;

import G1TBD.LABTBD.app.emergency.services.EmergencyService;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.models.UserRole;
import G1TBD.LABTBD.mongo.user.repositories.UserMongoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserMongoService {

    private final PointService pointService;
    private final EmergencyService emergencyService;
    private final UserMongoRepository userMongoRepository;

    public UserMongoService(UserMongoRepository userMongoRepository, PointService pointService, EmergencyService emergencyService) {
        this.userMongoRepository = userMongoRepository;
        this.pointService = pointService;
        this.emergencyService = emergencyService;
    }

    //--------------------------CREATE--------------------------

    public UserMongo saveUser(UserMongo user) {
        return userMongoRepository.insert(user);
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
            if(user != null && "VOLUNTEER".equals(user.getRole()) && user.isAvailability()){
                volunteers.add(user);
            }
        }

        return volunteers;
    }

 /*
    public double obtenerPromedioHabilidades() {
        long totalSkills = 0;
        long totalVoluntarios = 0;

        for (UserMongo user : userMongoRepository.findAllUsers()) {
            totalSkills += user.getSkills().size();
            totalVoluntarios++;
        }

        return totalVoluntarios == 0 ? 0 : (double) totalSkills / totalVoluntarios;
    }

 */

    public double obtenerPromedioHabilidades() {
        long totalSkills = 0;
        long totalVolunteers = 0;
        List<UserMongo> allVolunteers =
                userMongoRepository.findAllByRole(UserRole.valueOf("VOLUNTEER")).orElse(new ArrayList<>());

        for (UserMongo user : allVolunteers) {
            totalSkills += user.getSkills().size();
            totalVolunteers++;
        }

        if (totalVolunteers== 0) {
            return 0;
        } else {
            return (double) totalSkills / totalVolunteers;
        }

    }

    //--------------------------UPDATE--------------------------

    public void updateUser(UserMongo user) {
        // Asegúrate de que el usuario tiene un ID. Si no, maneja el caso adecuadamente (por ejemplo, lanzando una excepción).
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

    public boolean deleteUserById(String id) throws Exception {
        try {
            userMongoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
