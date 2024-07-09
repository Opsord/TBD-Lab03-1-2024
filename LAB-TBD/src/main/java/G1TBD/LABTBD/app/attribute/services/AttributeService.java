package G1TBD.LABTBD.app.attribute.services;

import G1TBD.LABTBD.app.attribute.repositories.AttributeRepository;
import G1TBD.LABTBD.app.attribute.entities.AttributeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private static final Logger logger = Logger.getLogger(AttributeService.class.getName());

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(AttributeEntity attribute) {
        attributeRepository.create(attribute.getAttribute());
        logger.info("Attribute created: " + attribute.getAttribute());
    }

    // ---------------------------READ---------------------------

    public List<AttributeEntity> getAll() {
        return attributeRepository.getAll();
    }

    public AttributeEntity getById(Long id) {
        return attributeRepository.getById(id);
    }

    // --------------------------UPDATE--------------------------

    public void update(AttributeEntity attribute) {
        attributeRepository.update(attribute.getAttribute_id(), attribute.getAttribute());
        logger.info("Attribute updated: " + attribute.getAttribute());
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        attributeRepository.delete(id);
        logger.info("Attribute deleted: " + id);
    }

}
