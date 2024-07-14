package G1TBD.LABTBD.app.user.services;

import G1TBD.LABTBD.app.user.entities.UserPointEntity;
import G1TBD.LABTBD.app.user.repositories.UserPointRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPointService {

    private final UserPointRepository userPointRepository;

    public UserPointService(UserPointRepository userPointRepository) {
        this.userPointRepository = userPointRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(String rut, Long point_id) {
        userPointRepository.create(rut, point_id);
    }

    // ---------------------------READ---------------------------

    public List<UserPointEntity> getAll() {
        return userPointRepository.getAll();
    }

    public UserPointEntity getById(Long person_point_id) {
        return userPointRepository.getById(person_point_id);
    }

    // --------------------------UPDATE--------------------------

    public void update(Long person_point_id, String rut, Long point_id) {
        userPointRepository.update(person_point_id, rut, point_id);
    }

    // --------------------------DELETE--------------------------

    public void delete(Long person_point_id) {
        userPointRepository.delete(person_point_id);
    }

}
