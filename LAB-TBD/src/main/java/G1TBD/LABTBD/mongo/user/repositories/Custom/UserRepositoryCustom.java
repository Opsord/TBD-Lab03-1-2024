package G1TBD.LABTBD.mongo.user.repositories.Custom;

import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.models.UserMongo;

import java.util.List;

public interface UserRepositoryCustom {
    //--------------------------CREATE--------------------------
    UserMongo createUser(UserMongo user);
    //--------------------------UPDATE--------------------------

    UserMongo updateUser(UserMongo user);

    //---------------------------READ---------------------------
    List<UserMongo> findAllUsers();
    UserMongo findUserById(String id);

    //--------------------------DELETE--------------------------
    void deleteUser(UserMongo user);
    void deleteUserById(String id);
}
