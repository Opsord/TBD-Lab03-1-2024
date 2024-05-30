package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.UserInstitutionEntity;
import G1TBD.LABTBD.repositories.UserInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserInstitutionService {

    private final UserInstitutionRepository userInstitutionRepository;
    private static final Logger logger = Logger.getLogger(UserInstitutionService.class.getName());

    @Autowired
    public UserInstitutionService(UserInstitutionRepository userInstitutionRepository) {
        this.userInstitutionRepository = userInstitutionRepository;
    }

    //--------------------------CREATE--------------------------
    public void create(UserInstitutionEntity userInstitution) {
        userInstitutionRepository.create(
                userInstitution.getUser().getRut(),
                userInstitution.getInstitution().getInstitution_id());
        logger.info("UserInstitution created: " + userInstitution);
    }


    //--------------------------UPDATE--------------------------
    public void update(UserInstitutionEntity userInstitution) {
        userInstitutionRepository.update(
                userInstitution.getUser_institution_id(),
                userInstitution.getUser().getRut(),
                userInstitution.getInstitution().getInstitution_id());
        logger.info("UserInstitution updated: " + userInstitution);
    }


    //---------------------------READ---------------------------
    public List<UserInstitutionEntity> getAll() {
        return userInstitutionRepository.getAll();
    }

    public UserInstitutionEntity getById(long id) {
        return userInstitutionRepository.getById(id);
    }

    public UserInstitutionEntity getByRut(String rut) {
        return userInstitutionRepository.getByRut(rut);
    }

    public List<UserInstitutionEntity> getByInstitutionId(long institution_id) {
        return userInstitutionRepository.getByInstitutionId(institution_id);
    }


    //--------------------------DELETE--------------------------
    public void delete(long id) {
        userInstitutionRepository.delete(id);
        logger.info("UserInstitution deleted: " + id);
    }



}
