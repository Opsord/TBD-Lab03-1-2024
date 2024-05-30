package G1TBD.LABTBD.controllers;

import G1TBD.LABTBD.entities.UserInstitutionEntity;
import G1TBD.LABTBD.services.UserInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user-institution")
@CrossOrigin(origins = "http://localhost:8090/api/user-institution")
public class UserInstitutionController {

    private final UserInstitutionService userInstitutionService;
    private static final Logger logger = Logger.getLogger(UserInstitutionController.class.getName());

    @Autowired
    public UserInstitutionController(UserInstitutionService userInstitutionService) {
        this.userInstitutionService = userInstitutionService;
    }
    //--------------------------CREATE--------------------------
    @PostMapping("/create")
    public void create(@RequestBody UserInstitutionEntity userInstitution) {
        logger.info("Creating user institution: " + userInstitution.toString());
        userInstitutionService.create(userInstitution);
    }


    //--------------------------UPDATE--------------------------
    @RequestMapping("/update")
    public void update(@RequestBody UserInstitutionEntity userInstitution) {
        logger.info("Updating user institution: " + userInstitution.toString());
        userInstitutionService.update(userInstitution);
    }


    //---------------------------READ---------------------------
    @GetMapping("/all")
    public List<UserInstitutionEntity> getAll(){return userInstitutionService.getAll();}

    @RequestMapping("/rut/{rut}")
    public UserInstitutionEntity getByRut(@PathVariable String rut) {return userInstitutionService.getByRut(rut);}

    @RequestMapping("/institution-id/{institution_id}")
    public List<UserInstitutionEntity> getByinstitution_id(@PathVariable long institution_id) {
        return userInstitutionService.getByInstitutionId(institution_id);
    }


    //--------------------------DELETE--------------------------
    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        logger.info("Deleting user institution with id: " + id);
        userInstitutionService.delete(id);
    }


}
