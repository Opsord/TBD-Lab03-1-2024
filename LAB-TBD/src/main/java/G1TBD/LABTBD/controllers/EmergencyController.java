package G1TBD.LABTBD.controllers;

import G1TBD.LABTBD.auth.entities.LocationRequest;
import G1TBD.LABTBD.data.SingleEmergencyData;
import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.entities.EmergencyEntity;
import G1TBD.LABTBD.entities.UserEntity;
import G1TBD.LABTBD.services.EmergencyService;
import G1TBD.LABTBD.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/emergencies")
@CrossOrigin(origins = "http://localhost:8090/emergencies")
public class EmergencyController {

    private final PointService pointService;
    private final EmergencyService emergencyService;
    private final UserService userService;
    private static final Logger logger = Logger.getLogger(EmergencyController.class.getName());

    @Autowired
    public EmergencyController(PointService pointService, EmergencyService emergencyService, UserService userService) {
        this.pointService = pointService;
        this.emergencyService = emergencyService;
        this.userService = userService;
    }

    String homeLinkRedirect = "redirect:/emergencies";

    //--------------------------CREATE--------------------------
    @PostMapping("/create")
    public ResponseEntity<EmergencyEntity> create(@RequestBody Map<String, Object> payload) {
        // Extract and convert location
        Map<String, Double> locationMap = (Map<String, Double>) payload.get("location");
        LocationRequest locationRequest = new LocationRequest(locationMap.get("latitude"), locationMap.get("longitude"));
        PointEntity newPoint = new PointEntity();
        newPoint.setLatitude(locationRequest.getLatitude());
        newPoint.setLongitude(locationRequest.getLongitude());
        pointService.create(newPoint);

        Long pointId = pointService.findByLatitudeAndLongitude(locationRequest.getLatitude(), locationRequest.getLongitude());
        PointEntity point = pointService.getById(pointId);
        logger.info("Retrieved point: " + point);

        // Extract and set coordinator
        String coordinatorRut = (String) payload.get("coordinator");
        UserEntity coordinator = userService.getByRut(coordinatorRut)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coordinator RUT"));

        // Create EmergencyEntity
        EmergencyEntity emergency = new EmergencyEntity();
        emergency.setTitle((String) payload.get("title"));
        emergency.setDescription((String) payload.get("description"));
        emergency.setStatus((Boolean) payload.get("status"));
        emergency.setLocation(point);
        emergency.setCoordinator(coordinator);

        emergencyService.create(emergency);
        logger.info("Emergency created: " + emergency);
        EmergencyEntity emergencyCreated = emergencyService.getLatestId(emergency);
        return new ResponseEntity<>(emergencyCreated, HttpStatus.CREATED);
    }
    //--------------------------UPDATE--------------------------
    @PutMapping("/update")
    public String update(@RequestBody EmergencyEntity emergency) {
        emergencyService.update(emergency);
        logger.info("Emergency updated: ");
        logger.info(emergency.toString());
        return homeLinkRedirect;
    }

    //Actualizar punto
    @PutMapping("/point/update")
    public void updatePoint(@RequestBody PointEntity point) {
        System.out.println(point.getPoint());
        pointService.update(point);
        logger.info("Point updated: " + point.getPoint());
    }


    //---------------------------READ---------------------------
    @GetMapping("/all")
    public List<EmergencyEntity> getAll(){return emergencyService.getAll();}

    @GetMapping("/active")
    public List<EmergencyEntity> getAllActive(){return  emergencyService.getAllActive();}

    @GetMapping("/id/{id}")
    public EmergencyEntity getById(@PathVariable long id) {return emergencyService.getById(id);}

    @GetMapping("/closed")
    public List<EmergencyEntity> getClosedEmergencies() {return emergencyService.getAllClosed();}

    @GetMapping("/nearby/{emergency_id}/{radius}/{quantity}")
    public List<UserEntity> getXNearbyVolunteers(@PathVariable long emergency_id,
                                                 @PathVariable double radius,
                                                 @PathVariable int quantity) {
        logger.info("Emergency ID: " + emergency_id);
        logger.info("Radius: " + radius);
        logger.info("Quantity: " + quantity);
        return emergencyService.getXNearbyVolunteers(emergency_id, radius, quantity);
    }


    @GetMapping("/closedEmergencyData")
    public List<SingleEmergencyData> getAllClosedEmergencyData() {
        return emergencyService.getAllClosedEmergencyData();
    }

    //--------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        emergencyService.delete(id);
        logger.info("Emergency deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }




}
