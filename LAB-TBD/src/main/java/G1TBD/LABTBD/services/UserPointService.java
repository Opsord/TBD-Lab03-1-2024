package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.UserPointEntity;
import G1TBD.LABTBD.repositories.UserPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPointService {

    private final UserPointRepository userPointRepository;

    @Autowired
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
