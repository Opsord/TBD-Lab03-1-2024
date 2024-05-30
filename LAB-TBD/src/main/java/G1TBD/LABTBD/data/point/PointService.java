package G1TBD.LABTBD.data.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PointService {

    private final PointRepository pointRepository;
    private static final Logger logger = Logger.getLogger(PointService.class.getName());

    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void create(PointEntity point) {
        pointRepository.create(point.getLatitude(), point.getLongitude());
        logger.info("Point created: " + point.getPoint());
    }

    public Long findByLatitudeAndLongitude(double latitude, double longitude) {
        return pointRepository.findIdByLatitudeAndLongitude(latitude, longitude);
    }


    public List<PointEntity> getAll() {
        return pointRepository.getAll();
    }

    public PointEntity getById(long id) {
        return pointRepository.getById(id);
    }

    public void update(PointEntity point) {
        pointRepository.update(point.getPoint_id(), point.getLatitude(), point.getLongitude());
        logger.info("Point updated: " + point.getPoint());
    }

    public void delete(long id) {
        pointRepository.delete(id);
        logger.info("Point deleted: " + id);
    }

    public List<PointEntity> getNearbyPoints(long point_id,int radius, int limit) {
        PointEntity point = pointRepository.getById(point_id);
        return pointRepository.findXNearbyPoints(point.getLatitude(), point.getLongitude(), radius, limit);
    }

}
