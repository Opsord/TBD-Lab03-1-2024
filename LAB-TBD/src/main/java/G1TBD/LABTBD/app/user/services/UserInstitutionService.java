package G1TBD.LABTBD.app.user.services;

import G1TBD.LABTBD.app.user.entities.UserInstitutionEntity;
import G1TBD.LABTBD.app.user.repositories.UserInstitutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserInstitutionService {

    private final UserInstitutionRepository userInstitutionRepository;
    private static final Logger logger = Logger.getLogger(UserInstitutionService.class.getName());

    public UserInstitutionService(UserInstitutionRepository userInstitutionRepository) {
        this.userInstitutionRepository = userInstitutionRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(UserInstitutionEntity userInstitution) {
        userInstitutionRepository.create(
                userInstitution.getRut(),
                userInstitution.getInstitution_id());
        logger.info("UserInstitution created: " + userInstitution);
    }

    // ---------------------------READ---------------------------

    public List<UserInstitutionEntity> getAll() {
        return userInstitutionRepository.getAll();
    }

    public UserInstitutionEntity getById(Long id) {
        return userInstitutionRepository.getById(id);
    }

    public UserInstitutionEntity getByRut(String rut) {
        return userInstitutionRepository.getByRut(rut);
    }

    public List<UserInstitutionEntity> getByInstitutionId(Long institution_id) {
        return userInstitutionRepository.getByInstitutionId(institution_id);
    }

    // --------------------------UPDATE--------------------------

    public void update(UserInstitutionEntity userInstitution) {
        userInstitutionRepository.update(
                userInstitution.getUser_institution_id(),
                userInstitution.getRut(),
                userInstitution.getInstitution_id());
        logger.info("UserInstitution updated: " + userInstitution);
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        userInstitutionRepository.delete(id);
        logger.info("UserInstitution deleted: " + id);
    }

}
