package G1TBD.LABTBD.app.emergency.services;

import G1TBD.LABTBD.app.emergency.entities.EmergencyEntity;
import G1TBD.LABTBD.app.emergency.entities.EmergencyPointEntity;
import G1TBD.LABTBD.app.emergency.entities.EmergencyUserEntity;
import G1TBD.LABTBD.app.emergency.repositories.EmergencyRepository;
import G1TBD.LABTBD.data.SingleEmergencyData;
import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.app.task.services.TaskService;
import G1TBD.LABTBD.app.task.entities.TaskEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.services.UserMongoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final TaskService taskService;
    private final EmergencyPointService emergencyPointService;
    private final EmergencyUserService emergencyUserService;
    private final PointService pointService;
    private final UserMongoService userMongoService;

    private static final Logger logger = Logger.getLogger(EmergencyService.class.getName());

    @Lazy
    public EmergencyService(EmergencyRepository emergencyRepository, TaskService taskService,
                            EmergencyPointService emergencyPointService, EmergencyUserService emergencyUserService, PointService pointService, UserMongoService userMongoService) {
        this.emergencyRepository = emergencyRepository;
        this.taskService = taskService;
        this.emergencyUserService = emergencyUserService;
        this.emergencyPointService = emergencyPointService;
        this.pointService = pointService;
        this.userMongoService = userMongoService;
    }

    // --------------------------CREATE--------------------------

    public void create(EmergencyEntity emergency) {
        emergencyRepository.create(
                emergency.getTitle(),
                emergency.getDescription(),
                emergency.isStatus());
        logger.info("Emergency created: " + emergency.getTitle());
    }

    // ---------------------------READ---------------------------

    public List<EmergencyEntity> getAll() {
        return emergencyRepository.getAll();
    }

    public List<EmergencyEntity> getAllActive() {
        return emergencyRepository.getAllActive();
    }

    public EmergencyEntity getById(Long id) {
        return emergencyRepository.getById(id);
    }

    public List<EmergencyEntity> getAllClosed() {
        return emergencyRepository.getAllClosed();
    }
/*
    public List<String> getAllVolunteers(Long emergency_id) {
        List<TaskEntity> taskList = taskService.getTasksByEmergencyId(emergency_id);
        List<String> volunteerList = new ArrayList<>();
        for (TaskEntity task : taskList) {

            volunteerList.addAll(taskService.getVolunteersByTask(task.getTask_id()));
            volunteerList.addAll(taskService.getVolunteersByTask(task.getTask_id()));
        }
        return volunteerList;
    }

 */

    public List<Optional<UserMongo>> getAllVolunteers(Long emergency_id) {
        //obtiene un listado de rut de los voluntarios en una emergencia
        List<String> ruts = emergencyRepository.getVolunteersByEmergencyId(emergency_id);
        List<Optional<UserMongo>> volunteerList = new ArrayList<>();
        for (String rut : ruts){
            volunteerList.add(userMongoService.getUserByRut(rut));
        }
        return  volunteerList;

    }

    public EmergencyEntity getLatestId(EmergencyEntity emergency) {
        return emergencyRepository.findLatestEmergencyId(emergency.getTitle(), emergency.getDescription());
    }

    public List<String> getXNearbyVolunteers(Long emergency_id, double radiusInKilometers, int limit) {
        logger.info("Working on emergency_id: " + emergency_id);
        EmergencyEntity emergency = getById(emergency_id);
        if (emergency == null) {
            throw new IllegalArgumentException("No emergency found with id: " + emergency_id);
        }
        double radiusInDegrees = radiusInKilometers / 111.12; // 1 degree = 111.12 km approx
        String role = "VOLUNTEER";
        boolean available = true;
        PointEntity location = emergencyPointService.getPointByEmergencyId(emergency_id);
        return (List<String>) pointService.getXNearbyVolunteers(location.getLatitude(), location.getLongitude(),
                radiusInDegrees, limit);
    }

    // Funcionalidad SQL 48 del laboratorio 1
    public List<SingleEmergencyData> getAllClosedEmergencyData() {
        List<EmergencyEntity> closedEmergencies = getAllClosed();
        List<SingleEmergencyData> singleEmergencyDataList = new ArrayList<>();

        for (EmergencyEntity emergency : closedEmergencies) {
            Long emergency_id = emergency.getEmergency_id();
            List<TaskEntity> taskList = taskService.getTasksByEmergencyId(emergency_id);
            List<String> volunteerList = emergencyRepository.getVolunteersByEmergencyId(emergency_id);
            SingleEmergencyData singleEmergencyData = new SingleEmergencyData(emergency.getTitle(),
                    volunteerList.size(), taskList.size());
            singleEmergencyDataList.add(singleEmergencyData);
        }
        return singleEmergencyDataList;
    }


    // --------------------------UPDATE--------------------------

    public void update(EmergencyEntity emergency) {
        emergencyRepository.update(
                emergency.getEmergency_id(),
                emergency.getTitle(),
                emergency.getDescription(),
                emergency.isStatus());
        logger.info("Emergency updated: " + emergency.getTitle());
    }

    public void updateEmergencyLocation(Long emergency_id, Long point_id) {
        if (emergencyPointService.getByEmergencyId(emergency_id) == null) {
            EmergencyPointEntity emergencyPointEntity = EmergencyPointEntity.builder()
                    .emergency_id(emergency_id)
                    .point_id(point_id)
                    .build();
            emergencyPointService.create(emergencyPointEntity);
        } else {
            EmergencyPointEntity emergencyPoint = emergencyPointService.getByEmergencyId(emergency_id);
            emergencyPoint.setPoint_id(point_id);
            emergencyPointService.update(emergencyPoint);
        }
    }

    public void updateEmergencyCoordinator(Long emergency_id, String rut) {
        if (emergencyUserService.getByEmergencyId(emergency_id) == null) {
            EmergencyUserEntity emergencyUserEntity = EmergencyUserEntity.builder()
                    .rut(rut)
                    .emergency_id(emergency_id)
                    .build();
            emergencyUserService.create(emergencyUserEntity);
        } else {
            EmergencyUserEntity emergencyUser = emergencyUserService.getByEmergencyId(emergency_id);
            emergencyUser.setRut(rut);
            emergencyUserService.update(emergencyUser);
        }
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        emergencyRepository.delete(id);
        logger.info("Emergency deleted: " + id);
    }

}
