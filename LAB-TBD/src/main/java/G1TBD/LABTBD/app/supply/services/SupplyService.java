package G1TBD.LABTBD.app.supply.services;

import G1TBD.LABTBD.app.supply.repositories.SupplyRepository;
import G1TBD.LABTBD.app.supply.entities.SupplyEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private static final Logger logger = Logger.getLogger(G1TBD.LABTBD.app.supply.services.SupplyService.class.getName());

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(SupplyEntity supply) {
        supplyRepository.create(supply.getName(), supply.getDescription(),
                supply.getClassification());
        logger.info("Supply created successfully");
    }

    // ---------------------------READ---------------------------

    public List<SupplyEntity> getAll() {
        return supplyRepository.getAll();
    }

    public SupplyEntity getById(Long id) {
        return supplyRepository.getById(id);
    }

    // --------------------------UPDATE--------------------------

    public void update(SupplyEntity supply) {
        supplyRepository.update(supply.getName(), supply.getDescription(),
                supply.getClassification(), supply.getSupply_id());
        logger.info("Supply updated successfully");
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        supplyRepository.delete(id);
        logger.info("Supply deleted successfully");
    }

}
