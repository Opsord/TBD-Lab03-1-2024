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

    // --------------------------CREATE--------------------------

    @PostMapping("/create")
    public SupplyEntity create(@RequestBody SupplyEntity supply) {
        SupplyEntity createdSupply = supplyService.create(supply);
        logger.info("Supply created: " + createdSupply);
        return createdSupply;
    }

    // --------------------------UPDATE--------------------------

    @PutMapping("/update")
    public SupplyEntity update(@RequestBody SupplyEntity supply) {
        SupplyEntity updatedSupply = supplyService.update(supply);
        logger.info("Supply updated: " + updatedSupply);
        return updatedSupply;
    }

    // ---------------------------READ---------------------------

    @GetMapping("/all")
    public List<SupplyEntity> getAll() {
        List<SupplyEntity> supplies = supplyService.getAll();
        logger.info("Retrieved all supplies: " + supplies);
        return supplies;
    }

    @GetMapping("/id/{id}")
    public SupplyEntity getById(@PathVariable Long id) {
        SupplyEntity supply = supplyService.getById(id);
        logger.info("Retrieved supply by ID (" + id + "): " + supply);
        return supply;
    }

    // --------------------------DELETE--------------------------

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        Long deletedId = supplyService.delete(id);
        logger.info("Supply deleted with ID: " + deletedId);
        return deletedId;
    }
}

