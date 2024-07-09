package G1TBD.LABTBD.app.user.services;

import G1TBD.LABTBD.data.point.PointEntity;
import G1TBD.LABTBD.data.point.PointService;
import G1TBD.LABTBD.app.user.entities.UserEntity;
import G1TBD.LABTBD.app.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PointService pointService;
    private final UserPointService userPointService;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository, PointService pointService, UserPointService userPointService) {
        this.userRepository = userRepository;
        this.pointService = pointService;
        this.userPointService = userPointService;
    }

    // --------------------------CREATE--------------------------

    public void create(UserEntity user) {

        userRepository.create(
                user.getRut(), user.getEmail(), user.getName(),
                user.getLast_name(), user.getBirth_date(), user.getSex(),
                user.getPassword(), user.getRole(), user.isAvailability());
        logger.info("Usuario creado: " + user.getRut());
    }

    public void createWithLocation(UserEntity user, PointEntity location) {
        create(user);
        updateUserLocation(user.getRut(), location);
    }

    // ---------------------------READ---------------------------

    public List<UserEntity> getAll() {
        return userRepository.getAll();
    }

    public UserEntity getByRut(String rut) {
        return userRepository.getByRut(rut).orElse(null);
    }

    public Optional<UserEntity> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public List<UserEntity> getByRole(String role) {
        return userRepository.getByRole(role);
    }

    public List<UserEntity> getVolunteers() {
        return userRepository.getVolunteers();
    }

    public List<UserEntity> getCoordinators() {
        return userRepository.getCoordinators();
    }

    public List<UserEntity> getByAvailability(boolean availability) {
        return userRepository.getByAvailability(availability);
    }

    public List<UserEntity> getXNearbyVolunteers(double latitude, double longitude,
                                                 double radius, int quantity, String role, boolean availability) {
        return userRepository.getXNearbyUsersFromPoint(latitude, longitude, radius, quantity, role, availability);
    }

    public List<UserEntity> getByEmergencyId(Long id) {
        return userRepository.getByEmergencyId(id);
    }

    // --------------------------UPDATE--------------------------

    public void update(UserEntity user) {
        userRepository.update(
                user.getRut(), user.getEmail(), user.getName(),
                user.getLast_name(), user.getBirth_date(), user.getSex(),
                user.getPassword(), user.getRole(), user.isAvailability());
        logger.info("Usuario actualizado: " + user.getRut());
    }

    public void updateUserLocation(String rut, PointEntity location) {
        Long pointId = pointService.findIdByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        if (pointId == null) {
            PointEntity newPoint = new PointEntity();
            newPoint.setLatitude(location.getLatitude());
            newPoint.setLongitude(location.getLongitude());
            pointService.create(newPoint);
            pointId = pointService.findIdByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        }
        userPointService.create(rut, pointId);
        logger.info("Ubicación actualizada para usuario: " + rut);
    }

    // --------------------------DELETE--------------------------

    public void delete(String rut) {
        userRepository.delete(rut);
        logger.info("Usuario eliminado: " + rut);
    }

}
