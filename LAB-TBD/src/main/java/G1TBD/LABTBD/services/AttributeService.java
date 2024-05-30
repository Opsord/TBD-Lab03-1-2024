package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.AttributeEntity;
import G1TBD.LABTBD.repositories.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private static final Logger logger = Logger.getLogger(AttributeService.class.getName());

    @Autowired
    public AttributeService(AttributeRepository attributeRepository) {this.attributeRepository = attributeRepository;}

    //--------------------------CREATE--------------------------
    public void create(AttributeEntity attribute) {
        attributeRepository.create(attribute.getAttribute());
        logger.info("Attribute created: " + attribute.getAttribute());
    }


    //--------------------------UPDATE--------------------------
    public void update(AttributeEntity attribute) {
        attributeRepository.update(attribute.getAttribute_id(), attribute.getAttribute());
        logger.info("Attribute updated: " + attribute.getAttribute());
    }


    //---------------------------READ---------------------------
    public List<AttributeEntity> getAll() {
        return attributeRepository.getAll();
    }

    public AttributeEntity getById(long id) {
        return attributeRepository.getById(id);
    }


    //--------------------------DELETE--------------------------
    public void delete(long id) {
        attributeRepository.delete(id);
        logger.info("Attribute deleted: " + id);
    }
}
