package G1TBD.LABTBD.app.user.services;

import G1TBD.LABTBD.app.user.entities.UserAttributeEntity;
import G1TBD.LABTBD.app.user.repositories.UserAttributeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserAttributeService {

    private final UserAttributeRepository userAttributeRepository;
    private static final Logger logger = Logger.getLogger(UserAttributeService.class.getName());

    public UserAttributeService(UserAttributeRepository userAttributeRepository) {
        this.userAttributeRepository = userAttributeRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(UserAttributeEntity userAttribute) {
        userAttributeRepository.create(
                userAttribute.getRut(),
                userAttribute.getAttribute_id());
        logger.info("UserAttribute created");
    }

    // ---------------------------READ---------------------------

    public List<UserAttributeEntity> getAll() {
        return userAttributeRepository.getAll();
    }

    public UserAttributeEntity getById(Long id) {
        return userAttributeRepository.getById(id);
    }

    public List<UserAttributeEntity> getByRut(String rut) {
        return userAttributeRepository.getByRut(rut);
    }

    public List<UserAttributeEntity> getByAttributeId(Long attribute_id) {
        return userAttributeRepository.getByAttributeId(attribute_id);
    }

    // --------------------------UPDATE--------------------------

    public void update(UserAttributeEntity userAttribute) {
        userAttributeRepository.update(
                userAttribute.getUser_attribute_id(),
                userAttribute.getRut(),
                userAttribute.getAttribute_id());
        logger.info("UserAttribute updated");
    }

    // --------------------------DELETE--------------------------
    public void delete(Long id) {
        userAttributeRepository.delete(id);
        logger.info("UserAttribute deleted");
    }

}
