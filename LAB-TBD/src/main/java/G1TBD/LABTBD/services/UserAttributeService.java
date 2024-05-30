package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.UserAttributeEntity;
import G1TBD.LABTBD.repositories.UserAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserAttributeService {

    private final UserAttributeRepository userAttributeRepository;
    private static final Logger logger = Logger.getLogger(UserAttributeService.class.getName());

    @Autowired
    public UserAttributeService(UserAttributeRepository userAttributeRepository) {
        this.userAttributeRepository = userAttributeRepository;
    }

    //--------------------------CREATE--------------------------
    public void create(UserAttributeEntity userAttribute) {
        userAttributeRepository.create(
                userAttribute.getUser().getRut(),
                userAttribute.getAttribute().getAttribute_id());
        logger.info("UserAttribute created");
    }


    //--------------------------UPDATE--------------------------
    public void update(UserAttributeEntity userAttribute) {
        userAttributeRepository.update(
                userAttribute.getUser_attribute_id(),
                userAttribute.getUser().getRut(),
                userAttribute.getAttribute().getAttribute_id());
        logger.info("UserAttribute updated");
    }


    //---------------------------READ---------------------------
    public List<UserAttributeEntity> getAll() {
        return userAttributeRepository.getAll();
    }

    public UserAttributeEntity getById(long id) {
        return userAttributeRepository.getById(id);
    }

    public List<UserAttributeEntity> getByRut(String rut) {return userAttributeRepository.getByRut(rut);}

    public List<UserAttributeEntity> getByAttributeId(long attribute_id) {
        return userAttributeRepository.getByAttributeId(attribute_id);
    }


    //--------------------------DELETE--------------------------
    public void delete(long id) {
        userAttributeRepository.delete(id);
        logger.info("UserAttribute deleted");
    }


}
