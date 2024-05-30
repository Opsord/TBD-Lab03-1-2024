package G1TBD.LABTBD.services;

import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointRepository;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.entities.UserEntity;
import G1TBD.LABTBD.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointService pointService;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(UserRepository userRepository, PointService pointService) {
        this.userRepository = userRepository;
        this.pointService = pointService;
    }

    //--------------------------CREATE--------------------------
    public void create(UserEntity user) {
        logger.info("Received user: " + user.toString());

        if (user.getLocation() == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }

        PointEntity location = user.getLocation();
        logger.info("Location received: " + location);

        PointEntity newPoint = new PointEntity();
        newPoint.setLatitude(location.getLatitude());
        newPoint.setLongitude(location.getLongitude());

        logger.info("Creating point: " + newPoint.toString());
        pointService.create(newPoint);

        Long pointId = pointService.findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        PointEntity pointEntity = pointService.getById(pointId);
        logger.info("Retrieved point: " + pointEntity);
        user.setLocation(pointEntity);

        userRepository.create(
                user.getRut(), user.getEmail(), user.getName(),
                user.getLastname(), user.getBirthdate(), user.getSex(),
                user.getPassword(), user.getRole(), user.isAvailability(),
                user.getLocation().getPoint_id());
        logger.info("Usuario creado: " + user.getRut());
    }



    //--------------------------UPDATE--------------------------
    public void update(UserEntity user) {
        userRepository.update(
                user.getRut(), user.getEmail(), user.getName(),
                user.getLastname(), user.getBirthdate(), user.getSex(),
                user.getPassword(), user.getRole(), user.isAvailability());
        logger.info("Usuario actualizado: " + user.getRut());
    }

    public void updateLocationByRut(PointEntity location, String rut) {
        userRepository.updateLocationByRut(location, rut);
    }


    //---------------------------READ---------------------------
    public List<UserEntity> getAll() {return userRepository.getAll();}

    public Optional<UserEntity> getByRut(String rut) {return userRepository.getByRut(rut);}

    public Optional<UserEntity> getByEmail(String email) {return userRepository.getByEmail(email);}

    public List<UserEntity> getByRole(String role) {return userRepository.getByRole(role);}

    public List<UserEntity> getVolunteers() {return userRepository.getVolunteers();}

    public List<UserEntity> getCoordinators() {return userRepository.getCoordinators();}

    public List<UserEntity> getByAvailability(boolean availability) {
        return userRepository.getByAvailability(availability);
    }

    public List<UserEntity> getXNearbyVolunteers(double latitude, double longitude,
                                                 double radius, int quantity, String role, boolean availability) {
        return userRepository.getXNearbyUsersFromPoint(latitude, longitude, radius, quantity, role, availability);
    }

    public List<UserEntity> getByEmergencyId(long id){return userRepository.getByEmergencyId(id);}


    //--------------------------DELETE--------------------------
    public void delete(String rut) {
        userRepository.delete(rut);
        logger.info("Usuario eliminado: " + rut);
    }

}
