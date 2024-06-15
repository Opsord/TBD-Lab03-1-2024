package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.EmergencyPointEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyPointRepository extends CrudRepository<EmergencyPointEntity, Long> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO emergency_point (emergency_id, point_id) " +
                        "VALUES (:emergency_id, :point_id)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("emergency_id") Long emergency_id,
                        @Param("point_id") Long point_id);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM emergency_point", nativeQuery = true)
        List<EmergencyPointEntity> getAll();

        @Query(value = "SELECT * FROM emergency_point " +
                        "WHERE emergency_point_id = :emergency_point_id", nativeQuery = true)
        EmergencyPointEntity getById(@Param("emergency_point_id") Long emergency_point_id);

        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE emergency_point SET emergency_id = :emergency_id, point_id = :point_id " +
                        "WHERE emergency_point_id = :emergency_point_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("emergency_point_id") Long emergency_point_id,
                        @Param("emergency_id") Long emergency_id,
                        @Param("point_id") Long point_id);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM emergency_point " +
                        "WHERE emergency_point_id = :emergency_point_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("emergency_point_id") Long emergency_point_id);

}
