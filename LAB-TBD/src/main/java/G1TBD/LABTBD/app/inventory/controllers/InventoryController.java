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
    public InventoryEntity create(@RequestBody InventoryEntity inventory) {
        InventoryEntity createdInventory = inventoryService.create(inventory);
        logger.info("Inventory entry created: " + createdInventory);
        return createdInventory;
    }

    // --------------------------UPDATE--------------------------
    @PutMapping("/update")
    public InventoryEntity update(@RequestBody InventoryEntity inventory) {
        InventoryEntity updatedInventory = inventoryService.update(inventory);
        logger.info("Inventory entry updated: " + updatedInventory);
        return updatedInventory;
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
    public Long delete(@PathVariable Long id) {
        Long deletedId = inventoryService.delete(id);
        logger.info("Inventory entry deleted with ID: " + deletedId);
        return deletedId;
    }
}
