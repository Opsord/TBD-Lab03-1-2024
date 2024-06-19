package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.EmergencyUserEntity;
import G1TBD.LABTBD.repositories.EmergencyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyUserService {

    private final EmergencyUserRepository emergencyUserRepository;

    @Autowired
    public EmergencyUserService(EmergencyUserRepository emergencyUserRepository) {
        this.emergencyUserRepository = emergencyUserRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(EmergencyUserEntity emergencyUser) {
        emergencyUserRepository.create(
                emergencyUser.getRut(),
                emergencyUser.getEmergency_id());
    }

    // ---------------------------READ---------------------------

    public List<EmergencyUserEntity> getAll() {
        return emergencyUserRepository.getAll();
    }

    public EmergencyUserEntity getById(Long emergency_person_id) {
        return emergencyUserRepository.getById(emergency_person_id);
    }

    public EmergencyUserEntity getByRut(String rut) {
        return emergencyUserRepository.getByRut(rut);
    }

    public EmergencyUserEntity getByEmergencyId(Long emergency_id) {
        return emergencyUserRepository.getByEmergencyId(emergency_id);
    }

    // --------------------------UPDATE--------------------------

    public void update(EmergencyUserEntity emergencyUser) {
        emergencyUserRepository.update(
                emergencyUser.getEmergency_person_id(),
                emergencyUser.getRut(),
                emergencyUser.getEmergency_id());
    }

    // --------------------------DELETE--------------------------

    public void delete(Long emergency_person_id) {
        emergencyUserRepository.delete(emergency_person_id);
    }

}
