package G1TBD.LABTBD.app.institution.services;

import G1TBD.LABTBD.app.institution.repositories.InstitutionRepository;
import G1TBD.LABTBD.app.institution.entities.InstitutionEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private static final Logger logger = Logger.getLogger(InstitutionService.class.getName());

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
