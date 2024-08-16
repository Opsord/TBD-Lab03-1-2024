package G1TBD.LABTBD.app.emergency.controllers;

import G1TBD.LABTBD.app.emergency.entities.EmergencyAttributeEntity;
import G1TBD.LABTBD.app.emergency.services.EmergencyAttributeService;
import G1TBD.LABTBD.auth.entities.LocationRequest;
import G1TBD.LABTBD.data.SingleEmergencyData;
import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.app.emergency.entities.EmergencyEntity;
import G1TBD.LABTBD.app.emergency.services.EmergencyService;
import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.services.UserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/emergencies")
@CrossOrigin(origins = "http://localhost:8090/emergencies")
public class EmergencyController {

    private final PointService pointService;
    private final EmergencyService emergencyService;
    private final UserMongoService userMongoService;
    private final EmergencyAttributeService emergencyAttributeService;

    private static final Logger logger = Logger.getLogger(EmergencyController.class.getName());

    @Autowired
    public EmergencyController(PointService pointService, EmergencyService emergencyService, UserMongoService userMongoService, EmergencyAttributeService emergencyAttributeService) {
        this.pointService = pointService;
        this.emergencyService = emergencyService;
        this.userMongoService = userMongoService;
        this.emergencyAttributeService = emergencyAttributeService;
    }

    String homeLinkRedirect = "redirect:/emergencies";

    // --------------------------CREATE--------------------------

    @PostMapping("/create")
    public ResponseEntity<EmergencyEntity> create(@RequestBody Map<String, Object> payload) {
        // Extract and convert location
        @SuppressWarnings("unchecked")
        Map<String, Double> locationMap = (Map<String, Double>) payload.get("location");
        // Create location request
        LocationRequest locationRequest = new LocationRequest(locationMap.get("latitude"),
                locationMap.get("longitude"));
        // Create point
        PointEntity newPoint = new PointEntity();
        newPoint.setLatitude(locationRequest.getLatitude());
        newPoint.setLongitude(locationRequest.getLongitude());
        pointService.create(newPoint);
        // Retrieve point ID
        Long pointId = pointService.findIdByLatitudeAndLongitude(locationRequest.getLatitude(),
                locationRequest.getLongitude());
        PointEntity point = pointService.getById(pointId);
        logger.info("Retrieved point: " + point);
        // Extract coordinator
        String coordinatorRut = (String) payload.get("coordinator");
        Optional<UserMongo> coordinator = userMongoService.getUserByRut(coordinatorRut);
        // Create EmergencyEntity
        EmergencyEntity emergency = new EmergencyEntity();
        emergency.setTitle((String) payload.get("title"));
        emergency.setDescription((String) payload.get("description"));
        emergency.setStatus((Boolean) payload.get("status"));
        emergencyService.create(emergency);
        // Retrieve emergency ID
        EmergencyEntity emergencyCreated = emergencyService.getLatestId(emergency);
        // Save emergency-point relationship
        emergencyService.updateEmergencyLocation(emergencyCreated.getEmergency_id(), point.getPoint_id());
        // Save emergency-user relationship
        if (coordinator.isPresent()){
            emergencyService.updateEmergencyCoordinator(emergencyCreated.getEmergency_id(), coordinator.get().getRut());
            logger.info("Emergency created: " + emergencyCreated);
        }
        // Return response
        return new ResponseEntity<>(emergencyCreated, HttpStatus.CREATED);
    }


    @PostMapping("/createEmergencyAttributes")
    public List<EmergencyAttributeEntity> createEmergencyAttributes(@RequestBody List<EmergencyAttributeEntity> emergencyAttributeList) {
        return emergencyAttributeService.createVarious(emergencyAttributeList);
    }


    // ---------------------------READ---------------------------

    @GetMapping("/all")
    public List<EmergencyEntity> getAll() {
        return emergencyService.getAll();
    }

    @GetMapping("/active")
    public List<EmergencyEntity> getAllActive() {
        return emergencyService.getAllActive();
    }

    @GetMapping("/id/{id}")
    public EmergencyEntity getById(@PathVariable Long id) {
        return emergencyService.getById(id);
    }

    @GetMapping("/closed")
    public List<EmergencyEntity> getClosedEmergencies() {
        return emergencyService.getAllClosed();
    }

    @GetMapping("/nearby/{emergency_id}/{radius}/{quantity}")
    public List<UserMongo> getXNearbyVolunteers(@PathVariable Long emergency_id,
                                                @PathVariable double radius,
                                                @PathVariable int quantity) {
        logger.info("Emergency ID: " + emergency_id);
        logger.info("Radius: " + radius);
        logger.info("Quantity: " + quantity);
        return userMongoService.getXNearbyVolunteers(emergency_id, radius, quantity);
    }

    @GetMapping("/closedEmergencyData")
    public List<SingleEmergencyData> getAllClosedEmergencyData() {
        return emergencyService.getAllClosedEmergencyData();
    }

    @GetMapping("/getAttributesByEmergencyId/{id}")
    public List<EmergencyAttributeEntity> getAllEmergencyAtribute(@PathVariable Long id){
        return emergencyAttributeService.getByEmergencyId(id);
    }

    @GetMapping("/getVolunteersByEmergencyId/{id}")
    public List<Optional<UserMongo>> getAllVolunteersByEmergencyId(@PathVariable Long id){
        return emergencyService.getAllVolunteers(id);
    }

    // --------------------------UPDATE--------------------------

    @PutMapping("/update")
    public String update(@RequestBody EmergencyEntity emergency) {
        emergencyService.update(emergency);
        logger.info("Emergency updated: ");
        logger.info(emergency.toString());
        return homeLinkRedirect;
    }

    // Actualizar punto
    @PutMapping("/point/update")
    public void updatePoint(@RequestBody PointEntity point) {
        logger.info("Point to update: " + point.getPoint());
        pointService.update(point);
        logger.info("Point updated: " + point.getPoint());
    }

    // --------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        emergencyService.delete(id);
        logger.info("Emergency deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }

}
