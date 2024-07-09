package G1TBD.LABTBD.app.emergency.services;

import G1TBD.LABTBD.app.emergency.entities.EmergencyAttributeEntity;
import G1TBD.LABTBD.app.emergency.repositories.EmergencyAttributeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EmergencyAttributeService {

    private final EmergencyAttributeRepository emergencyAttributeRepository;
    private static final Logger logger = Logger.getLogger(EmergencyAttributeService.class.getName());

    public EmergencyAttributeService(EmergencyAttributeRepository emergencyAttributeRepository) {
        this.emergencyAttributeRepository = emergencyAttributeRepository;
    }

    // --------------------------CREATE--------------------------

     public void create(EmergencyAttributeEntity emergencyAttribute) {
     emergencyAttributeRepository.create(
     emergencyAttribute.getEmergency_id(),
     emergencyAttribute.getAttribute_id(),
     emergencyAttribute.isCompatibility());
     logger.info("EmergencyAttribute created successfully");
     }


    public List<EmergencyAttributeEntity> createVarious(List<EmergencyAttributeEntity> emergencyAttributeEntityList) {
        List<EmergencyAttributeEntity> createdEmergencies = new ArrayList<>();

        for (EmergencyAttributeEntity emergencyAttribute : emergencyAttributeEntityList) {
            create(emergencyAttribute);
            createdEmergencies.add(emergencyAttribute);
        }
        return createdEmergencies;
    }

    // ---------------------------READ---------------------------

    public List<EmergencyAttributeEntity> getAll() {
        return emergencyAttributeRepository.getAll();
    }

    public EmergencyAttributeEntity getById(Long id) {
        return emergencyAttributeRepository.getById(id);
    }

    // --------------------------UPDATE--------------------------

    public void update(EmergencyAttributeEntity emergencyAttribute) {
        emergencyAttributeRepository.update(
                emergencyAttribute.getEmergency_attribute_id(),
                emergencyAttribute.getEmergency_id(),
                emergencyAttribute.getAttribute_id(),
                emergencyAttribute.isCompatibility());
        logger.info("EmergencyAttribute updated successfully");
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        emergencyAttributeRepository.delete(id);
        logger.info("EmergencyAttribute deleted successfully");
    }

}
