package G1TBD.LABTBD.services;

import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.entities.EmergencyPointEntity;
import G1TBD.LABTBD.repositories.EmergencyPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyPointService {

    private final EmergencyPointRepository emergencyPointRepository;
    private final PointService pointService;

    @Autowired
    public EmergencyPointService(EmergencyPointRepository emergencyPointRepository, PointService pointService) {
        this.emergencyPointRepository = emergencyPointRepository;
        this.pointService = pointService;
    }

    // --------------------------CREATE--------------------------

    public void create(EmergencyPointEntity emergencyPoint) {
        emergencyPointRepository.create(emergencyPoint.getEmergency_id(), emergencyPoint.getPoint_id());
    }

    // ---------------------------READ---------------------------

    public List<EmergencyPointEntity> getAll() {
        return emergencyPointRepository.getAll();
    }

    public EmergencyPointEntity getById(Long id) {
        return emergencyPointRepository.getById(id);
    }

    public EmergencyPointEntity getByEmergencyId(Long emergency_id) {
        return emergencyPointRepository.getByEmergencyId(emergency_id);
    }

    public PointEntity getPointByEmergencyId(Long emergency_id) {
        EmergencyPointEntity emergencyPointEntity = emergencyPointRepository.getByEmergencyId(emergency_id);
        return pointService.getById(emergencyPointEntity.getPoint_id());
    }

    // --------------------------UPDATE--------------------------

    public void update(EmergencyPointEntity emergencyPoint) {
        emergencyPointRepository.update(emergencyPoint.getEmergency_point_id(), emergencyPoint.getEmergency_id(), emergencyPoint.getPoint_id());
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        emergencyPointRepository.delete(id);
    }

}
