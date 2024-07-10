package G1TBD.LABTBD.mongo.user.services;

import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.repositories.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMongoService {
    @Autowired
    UserMongoRepository userMongoRepository;

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
