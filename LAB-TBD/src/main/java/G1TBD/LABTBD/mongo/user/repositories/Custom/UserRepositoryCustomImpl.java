package G1TBD.LABTBD.mongo.user.repositories.Custom;

import G1TBD.LABTBD.mongo.user.models.UserMongo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.Query;


import java.util.Date;
import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

    //--------------------------CREATE--------------------------
    @Override
    public UserMongo createUser(UserMongo user) {
        UserMongo newUser = new UserMongo();
        //voluntario.setId(UUID.randomUUID().toString());
        newUser.setId(String.valueOf(new ObjectId()));
        newUser.setRut(user.getRut());
        newUser.setName(user.getName());
        newUser.setLast_name(user.getLast_name());
        newUser.setBirth_date(user.getBirth_date());
        newUser.setEmail(user.getEmail());
        newUser.setSex(user.getSex());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        newUser.setAvailability(user.isAvailability());
        newUser.setSkills(user.getSkills());

        mongoTemplate.insert(newUser);
        return newUser;
    }


    //--------------------------UPDATE--------------------------
    @Override
    public UserMongo updateUser(UserMongo user) {
        UserMongo userUpdated = new UserMongo();
        userUpdated.setId(user.getId());
        userUpdated.setRut(user.getRut());
        userUpdated.setName(user.getName());
        userUpdated.setLast_name(user.getLast_name());
        userUpdated.setBirth_date(user.getBirth_date());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setSex(user.getSex());
        userUpdated.setPassword(user.getPassword());
        userUpdated.setRole(user.getRole());
        userUpdated.setAvailability(user.isAvailability());
        userUpdated.setSkills(user.getSkills());


        mongoTemplate.save(userUpdated);
        return userUpdated;
    }

    /*
    Segunda opción para hacer un update
    @Override
    public Voluntario updateVoluntario(Voluntario voluntario) {
        Query query = new Query(Criteria.where("id").is(voluntario.getId()));
        Update update = new Update()
                .set("nombre_voluntario", voluntario.getNombre_voluntario())
                .set("edad_voluntario", voluntario.getEdad_voluntario())
                .set("correo_voluntario", voluntario.getCorreo_voluntario());

        mongoTemplate.updateFirst(query, update, Voluntario.class);
        return voluntario;
    }
     */


    //---------------------------READ---------------------------
    @Override
    public List<UserMongo> findAllUsers() {
        return mongoTemplate.findAll(UserMongo.class);
    }

    @Override
    public UserMongo findUserById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, UserMongo.class);
    }


    //--------------------------DELETE--------------------------

    @Override
    public void deleteUser(UserMongo user) {
        mongoTemplate.remove(user);
    }

    @Override
    public void deleteUserById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, UserMongo.class);
    }


}
