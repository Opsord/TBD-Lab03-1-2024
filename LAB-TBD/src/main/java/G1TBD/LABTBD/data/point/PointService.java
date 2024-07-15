package G1TBD.LABTBD.data.point;

import G1TBD.LABTBD.app.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PointService {

    private final PointRepository pointRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(PointService.class.getName());

    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(PointEntity point) {
        pointRepository.create(point.getLatitude(), point.getLongitude());
        logger.info("Point created: " + point.getPoint());
    }

    // ---------------------------READ---------------------------

    public Long findIdByLatitudeAndLongitude(double latitude, double longitude) {
        return pointRepository.findIdByLatitudeAndLongitude(latitude, longitude);
    }

    public List<PointEntity> getAll() {
        return pointRepository.getAll();
    }

    public PointEntity getById(Long id) {
        return pointRepository.getById(id);
    }

    public List<PointEntity> getNearbyPoints(Long point_id, int radius, int limit) {
        PointEntity point = pointRepository.getById(point_id);
        return pointRepository.findXNearbyPoints(point.getLatitude(), point.getLongitude(), radius, limit);
    }

    public List<String> getXNearbyVolunteers(double latitude, double longitude,
                                        double radius, int quantity) {
        return pointRepository.getXNearbyUsersFromPoint(latitude, longitude, radius, quantity);
    }

    // --------------------------UPDATE--------------------------

    public void update(PointEntity point) {
        pointRepository.update(point.getPoint_id(), point.getLatitude(), point.getLongitude());
        logger.info("Point updated: " + point.getPoint());
    }

    // --------------------------DELETE--------------------------

    public void delete(Long id) {
        pointRepository.delete(id);
        logger.info("Point deleted: " + id);
    }



}
