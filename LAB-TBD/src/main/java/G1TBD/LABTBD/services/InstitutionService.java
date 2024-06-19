package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.InstitutionEntity;
import G1TBD.LABTBD.repositories.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private static final Logger logger = Logger.getLogger(InstitutionService.class.getName());

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(InstitutionEntity institution) {
        institutionRepository.create(institution.getName());
        logger.info("Institution created successfully");
    }

    // ---------------------------READ---------------------------

    public List<InstitutionEntity> getAll() {
        return institutionRepository.getAll();
    }

    public InstitutionEntity getById(Long id) {
        return institutionRepository.getById(id);
    }

    // --------------------------UPDATE--------------------------

    public void update(InstitutionEntity institution) {
        institutionRepository.update(institution.getInstitution_id(), institution.getName());
        logger.info("Institution updated successfully");
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        institutionRepository.delete(id);
        logger.info("Institution deleted successfully");
    }

}
