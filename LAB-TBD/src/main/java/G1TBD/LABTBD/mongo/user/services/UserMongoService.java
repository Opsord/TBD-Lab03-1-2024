package G1TBD.LABTBD.mongo.user.services;

import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.repositories.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMongoService {
    @Autowired
    UserMongoRepository userMongoRepository;

    @Autowired
    PointService pointService;

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
        long totalUsers = 0;
        List<UserMongo> allUsers = userMongoRepository.findAllUsers();

        for (UserMongo user : allUsers) {
            totalSkills += user.getSkills().size();
            totalUsers++;
        }

        if (totalUsers== 0) {
            return 0;
        } else {
            return (double) totalSkills / totalUsers;
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
