package G1TBD.LABTBD.controllers;

import G1TBD.LABTBD.entities.InstitutionEntity;
import G1TBD.LABTBD.services.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/institutions")
@CrossOrigin(origins = "http://localhost:8090/institutions")
public class InstitutionController {

    private final InstitutionService institutionService;
    private static final Logger logger = Logger.getLogger(InstitutionController.class.getName());

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    String homeLinkRedirect = "redirect:/institutions";

    //--------------------------CREATE--------------------------
    @PostMapping("/create")
    public String create(@RequestBody InstitutionEntity institution) {
        institutionService.create(institution);
        logger.info("Institution created: ");
        logger.info(institution.toString());
        return homeLinkRedirect;
    }


    //--------------------------UPDATE--------------------------
    @PutMapping("/update")
    public String update(@RequestBody InstitutionEntity institution) {
        institutionService.update(institution);
        logger.info("Institution updated: ");
        logger.info(institution.toString());
        return homeLinkRedirect;
    }


    //---------------------------READ---------------------------
    @GetMapping("/all")
    public List<InstitutionEntity> getAll() {return institutionService.getAll();}

    @GetMapping("/id/{id}")
    public InstitutionEntity getById(@PathVariable long id) {return institutionService.getById(id);}


    //--------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        institutionService.delete(id);
        logger.info("Institution deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }
}
