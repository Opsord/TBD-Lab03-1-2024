package G1TBD.LABTBD.app.inventory.services;

import G1TBD.LABTBD.app.inventory.repositories.InventoryRepository;
import G1TBD.LABTBD.app.inventory.entities.InventoryEntity;
import G1TBD.LABTBD.app.supply.services.SupplyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private static final Logger logger = Logger.getLogger(G1TBD.LABTBD.app.supply.services.SupplyService.class.getName());

    public InventoryService(InventoryRepository inventoryRepository, SupplyService supplyService) {
        this.inventoryRepository = inventoryRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(InventoryEntity inventory) {
        inventoryRepository.create(inventory.getSupply_id(), inventory.getEmergency_id(), inventory.getRequested(),
                inventory.getStock(), inventory.getMissing(), inventory.getPriority());
        logger.info("Inventory entry created successfully");
    }

    // ---------------------------READ---------------------------

    public List<InventoryEntity> getAll() {
        return inventoryRepository.getAll();
    }

    public InventoryEntity getById(Long id) {
        return inventoryRepository.getById(id);
    }

    // --------------------------UPDATE--------------------------

    public void update(InventoryEntity inventory) {
        Integer missingAmount = inventory.getRequested() - inventory.getStock();
        if (missingAmount <= 0) {
            inventory.setMissing("Suficiente");
        } else {
            inventory.setMissing(String.valueOf(missingAmount));
        }
        inventoryRepository.update(inventory.getSupply_id(), inventory.getEmergency_id(), inventory.getRequested(),
                inventory.getStock(), inventory.getMissing(), inventory.getPriority(), inventory.getInventory_id());
        logger.info("Inventory entry updated successfully");
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        inventoryRepository.delete(id);
        logger.info("Inventory entry deleted successfully");
    }

}
