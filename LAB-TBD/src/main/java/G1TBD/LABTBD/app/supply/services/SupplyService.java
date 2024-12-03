package G1TBD.LABTBD.app.supply.services;

import G1TBD.LABTBD.app.supply.entities.SupplyEntity;
import G1TBD.LABTBD.app.supply.repositories.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private static final Logger logger = Logger.getLogger(SupplyService.class.getName());

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    // --------------------------CREATE--------------------------

    public SupplyEntity create(SupplyEntity supply) {
        // Saving the supply entity
        SupplyEntity createdSupply = supplyRepository.save(supply);
        logger.info("Supply created successfully with ID: " + createdSupply.getSupply_id());
        return createdSupply;
    }

    // ---------------------------READ---------------------------

    public List<SupplyEntity> getAll() {
        return supplyRepository.findAll();
    }

    public SupplyEntity getById(Long id) {
        return supplyRepository.findById(id).orElse(null);
    }

    // --------------------------UPDATE--------------------------

    public SupplyEntity update(SupplyEntity supply) {
        SupplyEntity updatedSupply = supplyRepository.saveAndFlush(supply);
        logger.info("Supply updated successfully with ID: " + updatedSupply.getSupply_id());
        return updatedSupply;
    }

    // --------------------------DELETE--------------------------

    public Long delete(Long id) {
        supplyRepository.deleteById(id);
        logger.info("Supply deleted successfully with ID: " + id);
        return id;
    }
}
