package G1TBD.LABTBD.app.inventory.controllers;

import G1TBD.LABTBD.app.inventory.entities.InventoryEntity;
import G1TBD.LABTBD.app.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/inventoryEntries")
public class InventoryController {

    private final InventoryService inventoryService;
    private static final Logger logger = Logger.getLogger(G1TBD.LABTBD.app.supply.controllers.SupplyController.class.getName());

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    String homeLinkRedirect = "redirect:/inventoryEntries";

    // --------------------------CREATE--------------------------
    @PostMapping("/create")
    public String create(@RequestBody InventoryEntity inventory) {
        inventoryService.create(inventory);
        logger.info("Inventory entry created: ");
        logger.info(inventory.toString());
        return homeLinkRedirect;
    }

    // --------------------------UPDATE--------------------------
    @PutMapping("/update")
    public String update(@RequestBody InventoryEntity inventory) {
        inventoryService.update(inventory);
        logger.info("Inventory entry updated: ");
        logger.info(inventory.toString());
        return homeLinkRedirect;
    }

    // ---------------------------READ---------------------------
    @GetMapping("/all")
    public List<InventoryEntity> getAll() {
        return inventoryService.getAll();
    }

    @GetMapping("/id/{id}")
    public InventoryEntity getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    // --------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        inventoryService.delete(id);
        logger.info("Inventory entry deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }
}
