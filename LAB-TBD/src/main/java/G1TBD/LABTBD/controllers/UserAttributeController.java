package G1TBD.LABTBD.controllers;

import G1TBD.LABTBD.entities.UserAttributeEntity;
import G1TBD.LABTBD.services.UserAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user-attribute")
@CrossOrigin(origins = "http://localhost:8090/api/user-attribute")
public class UserAttributeController {

    private final UserAttributeService userAttributeService;
    private static final Logger logger = Logger.getLogger(UserAttributeController.class.getName());

    @Autowired
    public UserAttributeController(UserAttributeService userAttributeService) {
        this.userAttributeService = userAttributeService;
    }

    //--------------------------CREATE--------------------------
    @PostMapping("/create")
    public void create(@RequestBody UserAttributeEntity userAttribute) {
        logger.info("Creating user attribute: " + userAttribute.toString());
        userAttributeService.create(userAttribute);
    }


    //--------------------------UPDATE--------------------------
    @PutMapping("/update")
    public void update(@RequestBody UserAttributeEntity userAttribute) {
        logger.info("Updating user attribute: " + userAttribute.toString());
        userAttributeService.update(userAttribute);
    }


    //---------------------------READ---------------------------
    @GetMapping("/all")
    public List<UserAttributeEntity> getAll() {return userAttributeService.getAll();}

    @GetMapping("/rut/{rut}")
    public List<UserAttributeEntity> getByRut(@PathVariable String rut) {return userAttributeService.getByRut(rut);}

    @GetMapping("/attribute-id/{attribute_id}")
    public List<UserAttributeEntity> getByattribute_id(@PathVariable long attribute_id) {
        return userAttributeService.getByAttributeId(attribute_id);
    }


    //--------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        logger.info("Deleting user attribute with id: " + id);
        userAttributeService.delete(id);
    }

}
