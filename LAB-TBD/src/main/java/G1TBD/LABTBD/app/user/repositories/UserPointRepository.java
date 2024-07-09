package G1TBD.LABTBD.app.user.repositories;

import G1TBD.LABTBD.app.user.entities.UserPointEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPointRepository extends CrudRepository<UserPointEntity, Long> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO person_point (rut, point_id) " +
                        "VALUES (:rut, :point_id)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(String rut, Long point_id);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM person_point", nativeQuery = true)
        List<UserPointEntity> getAll();

        @Query(value = "SELECT * FROM person_point " +
                        "WHERE person_point_id = :person_point_id", nativeQuery = true)
        UserPointEntity getById(Long person_point_id);

        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE person_point SET rut = :rut, point_id = :point_id " +
                        "WHERE person_point_id = :person_point_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(Long person_point_id, String rut, Long point_id);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM person_point " +
                        "WHERE person_point_id = :person_point_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(Long person_point_id);

}