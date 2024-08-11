package G1TBD.LABTBD.mongo.user.controllers;

import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.services.UserMongoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usersMongo")
//@CrossOrigin(origins = "http://localhost:8090/usersMongo")
public class UserMongoController {

    private final UserMongoService userMongoService;

    public UserMongoController(UserMongoService userMongoService){
        this.userMongoService = userMongoService;
    }

    //--------------------------CREATE--------------------------

    @PostMapping("/")
    public ResponseEntity<String> saveUser(@RequestBody UserMongo user){
        try {
            userMongoService.saveUser(user);
            return new ResponseEntity<>("Usuario guardado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------------------READ---------------------------

    @GetMapping("/")
    public ResponseEntity<List<UserMongo>> getUsers() {
        try {
            List<UserMongo> users = userMongoService.getUsers();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMongo> findUserById(@PathVariable String id) {
        try {
            Optional<UserMongo> user = userMongoService.getUserById(id);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Funcionalidad laboratorio 3
    @GetMapping("/promedio-habilidades")
    public double obtenerPromedioHabilidades() {
        return userMongoService.obtenerPromedioHabilidades();
    }

    //--------------------------UPDATE--------------------------

    @PutMapping("/")
    public String updateVoluntario(@RequestBody UserMongo user){
        userMongoService.updateUser(user);
        return "Usuario actualizado";
    }

    //--------------------------DELETE--------------------------

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) throws Exception {
        userMongoService.deleteUserById(id);
        return "Usuario eliminado";
    }

}
