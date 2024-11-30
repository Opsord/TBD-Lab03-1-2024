package G1TBD.LABTBD.app.supply.controllers;

import G1TBD.LABTBD.app.supply.entities.SupplyEntity;
import G1TBD.LABTBD.app.supply.services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    private final SupplyService supplyService;
    private static final Logger logger = Logger.getLogger(SupplyController.class.getName());

    @Autowired
    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    String homeLinkRedirect = "redirect:/supplies";

    // --------------------------CREATE--------------------------
    @PostMapping("/create")
    public String create(@RequestBody SupplyEntity supply) {
        supplyService.create(supply);
        logger.info("Supply created: ");
        logger.info(supply.toString());
        return homeLinkRedirect;
    }

    // --------------------------UPDATE--------------------------
    @PutMapping("/update")
    public String update(@RequestBody SupplyEntity supply) {
        supplyService.update(supply);
        logger.info("Supply updated: ");
        logger.info(supply.toString());
        return homeLinkRedirect;
    }

    // ---------------------------READ---------------------------
    @GetMapping("/all")
    public List<SupplyEntity> getAll() {
        return supplyService.getAll();
    }

    @GetMapping("/id/{id}")
    public SupplyEntity getById(@PathVariable Long id) {
        return supplyService.getById(id);
    }

    // --------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        supplyService.delete(id);
        logger.info("Supply deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }
}
