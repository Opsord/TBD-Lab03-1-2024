package G1TBD.LABTBD.controllers;

import G1TBD.LABTBD.entities.AttributeEntity;
import G1TBD.LABTBD.entities.EmergencyEntity;
import G1TBD.LABTBD.services.AttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attributes")
@CrossOrigin(origins = "http://localhost:8090/attributes")
public class AttributeController {

    private final AttributeService attributeService;
    private static final Logger logger = LoggerFactory.getLogger(AttributeController.class);

    @Autowired
    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    String homeLinkRedirect = "redirect:/attributes";

    //--------------------------CREATE--------------------------
    @PostMapping("/create")
    public String create(@RequestBody AttributeEntity attribute) {
        attributeService.create(attribute);
        logger.info("Attribute created: ");
        logger.info(attribute.toString());
        return homeLinkRedirect;
    }


    //--------------------------UPDATE--------------------------
    @PutMapping("/update")
    public String update(@RequestBody AttributeEntity attribute) {
        attributeService.update(attribute);
        logger.info("Attribute updated: ");
        logger.info(attribute.toString());
        return homeLinkRedirect;
    }


    //---------------------------READ---------------------------
    @GetMapping("/all")
    public List<AttributeEntity> getAll() {
        return attributeService.getAll();
    }

    @GetMapping("/id/{id}")
    public AttributeEntity getById(@PathVariable long id) {
        return attributeService.getById(id);
    }


    //--------------------------DELETE--------------------------

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        attributeService.delete(id);
        logger.info("Attribute deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }

}
