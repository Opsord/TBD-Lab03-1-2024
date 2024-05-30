package G1TBD.LABTBD.services;

import G1TBD.LABTBD.data.SingleEmergencyData;
import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.entities.EmergencyEntity;
import G1TBD.LABTBD.entities.TaskEntity;
import G1TBD.LABTBD.entities.UserEntity;
import G1TBD.LABTBD.repositories.EmergencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final TaskService taskService;
    private final UserService userService;
    private static final Logger logger = Logger.getLogger(EmergencyService.class.getName());
    private final PointService pointService;

    @Autowired
    public EmergencyService(EmergencyRepository emergencyRepository, TaskService taskService, UserService userService, PointService pointService) {
        this.emergencyRepository = emergencyRepository;
        this.taskService = taskService;
        this.userService = userService;
        this.pointService = pointService;
    }

    //--------------------------CREATE--------------------------
    public void create(EmergencyEntity emergency) {
        emergencyRepository.create(
                emergency.getTitle(),
                emergency.getDescription(),
                emergency.isStatus(),
                emergency.getCoordinator().getRut(),
                emergency.getLocation().getPoint_id());
        logger.info("Emergency created: " + emergency.getTitle());
    }


    //--------------------------UPDATE--------------------------
    public void update(EmergencyEntity emergency) {
        emergencyRepository.update(
                emergency.getEmergency_id(),
                emergency.getTitle(),
                emergency.getDescription(),
                emergency.isStatus(),
                emergency.getCoordinator().getRut());
        logger.info("Emergency updated: " + emergency.getTitle());
    }


    //---------------------------READ---------------------------
    public List<EmergencyEntity> getAll() {return emergencyRepository.getAll();}

    public List<EmergencyEntity> getAllActive() {return emergencyRepository.getAllActive();}

    public EmergencyEntity getById(long id) {return emergencyRepository.getById(id);}

    public List<EmergencyEntity> getAllClosed() {return emergencyRepository.getAllClosed();}

    public EmergencyEntity getByLocation(double latitude, double longitude) {
        return emergencyRepository.getByLocation(latitude, longitude);
    }

    public List<UserEntity> getAllVolunteers(long emergency_id) {
        List<TaskEntity> taskList = taskService.getByEmergencyId(emergency_id);
        List<UserEntity> volunteerList = new ArrayList<>();
        for (TaskEntity task : taskList) {
            volunteerList.addAll(taskService.getAllVolunteers(task.getTask_id()));
        }
        return volunteerList;
    }

    public EmergencyEntity getLatestId(EmergencyEntity emergency) {
        return emergencyRepository.findLatestEmergencyId(emergency.getTitle(), emergency.getDescription(), emergency.getCoordinator().getRut());
    }

    public List<UserEntity> getXNearbyVolunteers(long emergency_id, double radiusInKilometers, int limit) {
        logger.info("Working on emergency_id: " + emergency_id);
        EmergencyEntity emergency = getById(emergency_id);
        if (emergency == null) {
            throw new IllegalArgumentException("No emergency found with id: " + emergency_id);
        }
        double radiusInDegrees = radiusInKilometers / 111.12; // 1 degree = 111.12 km approx
        String role = "VOLUNTEER";
        boolean available = true;
        PointEntity location = emergency.getLocation();
        return userService.getXNearbyVolunteers(location.getLatitude(), location.getLongitude(),
                radiusInDegrees, limit, role, available);
    }

    //Funcionalidad SQL 48 del laboratorio 1
    public List<SingleEmergencyData> getAllClosedEmergencyData(){
        List<EmergencyEntity> closedEmergencies = getAllClosed();
        List<SingleEmergencyData> singleEmergencyDataList = new ArrayList<>();

    for (EmergencyEntity emergency : closedEmergencies) {
        long emergency_id = emergency.getEmergency_id();
        List<TaskEntity> taskList = taskService.getByEmergencyId(emergency_id);
        List<UserEntity> volunteerList = userService.getByEmergencyId(emergency_id);
        SingleEmergencyData singleEmergencyData = new SingleEmergencyData(emergency.getTitle(), volunteerList.size(), taskList.size());
        singleEmergencyDataList.add(singleEmergencyData);
        }
        return singleEmergencyDataList;
    }


    //--------------------------DELETE--------------------------
    public void delete(long id) {
        emergencyRepository.delete(id);
        logger.info("Emergency deleted: " + id);
    }


}
