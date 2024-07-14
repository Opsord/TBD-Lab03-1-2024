package G1TBD.LABTBD.mongo.user.controllers;

import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.services.UserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersMongo")
//@CrossOrigin(origins = "http://localhost:8090/usersMongo")
public class UserMongoController {
    @Autowired
    private UserMongoService userMongoService;

    //--------------------------CREATE--------------------------
    @PostMapping("/")
    public ResponseEntity<?> saveUser(@RequestBody UserMongo user){
        try {
            UserMongo newUser = userMongoService.saveUser(user);
            return new ResponseEntity<UserMongo>(newUser, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //--------------------------UPDATE--------------------------
    @PutMapping("/")
    public ResponseEntity<UserMongo> updateVoluntario(@RequestBody UserMongo user){
        UserMongo userUpdated = userMongoService.updateUser(user);
        return ResponseEntity.ok(userUpdated);
    }


    //---------------------------READ---------------------------
    @GetMapping("/")
    public ResponseEntity<?> findAllUsers(){
        try {
            List<UserMongo> users = userMongoService.getUsers();
            return new ResponseEntity<List<UserMongo>>(users, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getCause().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMongo> findUserById(@PathVariable String id) {
        try {
            UserMongo user = userMongoService.getUserById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/promedio-habilidades")
    public double obtenerPromedioHabilidades() {
        return userMongoService.obtenerPromedioHabilidades();
    }


    //--------------------------DELETE--------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable String id) throws Exception {
        var isDeleted = userMongoService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
