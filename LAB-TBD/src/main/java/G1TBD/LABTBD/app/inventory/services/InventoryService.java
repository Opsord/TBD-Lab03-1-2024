package G1TBD.LABTBD.app.inventory.services;

import G1TBD.LABTBD.app.inventory.repositories.InventoryRepository;
import G1TBD.LABTBD.app.inventory.entities.InventoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private static final Logger logger = Logger.getLogger(InventoryService.class.getName());

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    // --------------------------CREATE--------------------------

    public InventoryEntity create(InventoryEntity inventory) {
        // Handle null values safely
        Integer requestedValue = inventory.getRequested() != null ? inventory.getRequested() : 0;
        Integer stockValue = inventory.getStock() != null ? inventory.getStock() : 0;

        Integer missingAmount = requestedValue - stockValue;
        if (missingAmount <= 0) {
            inventory.setMissing("Suficiente");
        } else {
            inventory.setMissing(String.valueOf(missingAmount));
        }

        // Save the entity
        InventoryEntity newInventory = inventoryRepository.save(inventory);
        logger.info("Inventory entry created successfully");
        return newInventory;
    }


    // ---------------------------READ---------------------------

    public List<InventoryEntity> getAll() {
        return inventoryRepository.findAll();
    }

    public InventoryEntity getById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    // --------------------------UPDATE--------------------------

    public InventoryEntity update(InventoryEntity inventory) {
        // Handle null values safely
        Integer requestedValue = inventory.getRequested() != null ? inventory.getRequested() : 0;
        Integer stockValue = inventory.getStock() != null ? inventory.getStock() : 0;

        Integer missingAmount = requestedValue - stockValue;
        if (missingAmount <= 0) {
            inventory.setMissing("Suficiente");
        } else {
            inventory.setMissing(String.valueOf(missingAmount));
        }

        // Save the updated entity
        InventoryEntity updatedInventory = inventoryRepository.saveAndFlush(inventory);
        logger.info("Inventory entry updated successfully");
        return updatedInventory;
    }

    // --------------------------DELETE--------------------------

    public Long delete(Long id) {
        inventoryRepository.deleteById(id);
        logger.info("Inventory entry deleted successfully with ID: " + id);
        return id;
    }
}
