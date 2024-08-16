package G1TBD.LABTBD.app.emergency.repositories;

import G1TBD.LABTBD.app.emergency.entities.EmergencyUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyUserRepository extends CrudRepository<EmergencyUserEntity, Long> {

        // --------------------------CREATE--------------------------
        @Query(value = "INSERT INTO emergency_person (rut, emergency_id) " +
                        "VALUES (:rut, :emergency_id)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("rut") String rut,
                        @Param("emergency_id") Long emergency_id);

        // ---------------------------READ---------------------------
        @Query(value = "SELECT * FROM emergency_person", nativeQuery = true)
        List<EmergencyUserEntity> getAll();

        @Query(value = "SELECT * FROM emergency_person " +
                        "WHERE emergency_person_id = :emergency_person_id", nativeQuery = true)
        EmergencyUserEntity getById(@Param("emergency_person_id") Long emergency_person_id);

        @Query(value = "SELECT * FROM emergency_person " +
                        "WHERE emergency_id = :emergency_id", nativeQuery = true)
        EmergencyUserEntity getByEmergencyId(@Param("emergency_id") Long emergency_id);

        @Query(value = "SELECT * FROM emergency_person " +
                        "WHERE rut = :rut", nativeQuery = true)
        EmergencyUserEntity getByRut(@Param("rut") String rut);

        // --------------------------UPDATE--------------------------
        @Query(value = "UPDATE emergency_person SET rut = :rut, emergency_id = :emergency_id " +
                        "WHERE emergency_person_id = :emergency_person_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("emergency_person_id") Long emergency_person_id,
                        @Param("rut") String rut,
                        @Param("emergency_id") Long emergency_id);

        // --------------------------DELETE--------------------------
        @Query(value = "DELETE FROM emergency_person " +
                        "WHERE emergency_person_id = :emergency_person_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("emergency_person_id") Long emergency_person_id);

}
