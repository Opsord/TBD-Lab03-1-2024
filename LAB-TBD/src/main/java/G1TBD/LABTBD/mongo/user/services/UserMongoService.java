package G1TBD.LABTBD.mongo.user.services;

import G1TBD.LABTBD.app.emergency.services.EmergencyService;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.repositories.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMongoService {
    @Autowired
    UserMongoRepository userMongoRepository;

    @Autowired
    PointService pointService;

    @Autowired
    EmergencyService emergencyService;
    //--------------------------CREATE--------------------------
    public UserMongo saveUser(UserMongo user) {
        return userMongoRepository.createUser(user);
    }


    //--------------------------UPDATE--------------------------
    public UserMongo updateUser(UserMongo user){
        return userMongoRepository.updateUser(user);
    }

    //---------------------------READ---------------------------
    public List<UserMongo> getUsers(){
        return userMongoRepository.findAllUsers();
    }

    public UserMongo getUserById(String id) throws Exception {
        try {
            UserMongo user = userMongoRepository.findUserById(id);
            return user;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }



    public List<UserMongo> getXNearbyVolunteers(Long emergency_id, double radius, int quantity){
        //Listado de los ruts de los usuarios (voluntarios y coordinadores) que están cerca
        List<String> NearbyVolunteersRut = emergencyService.getXNearbyVolunteers(emergency_id, radius, quantity);
        List<UserMongo> volunteers = new ArrayList<>();

        for(String rut: NearbyVolunteersRut){
            UserMongo user = userMongoRepository.findUserByRut(rut);

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
        List<UserMongo> allVolunteers = userMongoRepository.findAllVolunteers();

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


    //--------------------------DELETE--------------------------
    public boolean deleteUser(UserMongo user) throws Exception {
        try {
            userMongoRepository.deleteUser(user);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean deleteUserById(String id) throws Exception {
        try {
            userMongoRepository.deleteUserById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
