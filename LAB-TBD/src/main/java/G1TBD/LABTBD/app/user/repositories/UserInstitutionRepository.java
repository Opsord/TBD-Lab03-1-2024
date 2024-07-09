package G1TBD.LABTBD.app.user.repositories;

import G1TBD.LABTBD.app.user.entities.UserInstitutionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInstitutionRepository extends CrudRepository<UserInstitutionEntity, Long> {

        // --------------------------CREATE--------------------------
        @Query(value = "INSERT INTO person_institution (rut, institution_id) " +
                        "VALUES (:rut, :institution_id)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("rut") String rut, @Param("institution_id") Long institution_id);

        // --------------------------UPDATE--------------------------
        @Query(value = "UPDATE person_institution SET rut = :rut, institution_id = :institution_id " +
                        "WHERE person_institution_id = :user_institution_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("user_institution_id") Long user_institution_id, @Param("rut") String rut,
                        @Param("institution_id") Long institution_id);

        // ---------------------------READ---------------------------
        @Query(value = "SELECT * FROM person_institution", nativeQuery = true)
        List<UserInstitutionEntity> getAll();

        @Query(value = "SELECT * FROM person_institution WHERE person_institution_id = :user_institution_id", nativeQuery = true)
        UserInstitutionEntity getById(@Param("user_institution_id") Long user_institution_id);

        @Query(value = "SELECT * FROM person_institution WHERE rut = :rut", nativeQuery = true)
        UserInstitutionEntity getByRut(@Param("rut") String rut);

        @Query(value = "SELECT * FROM person_institution WHERE institution_id = :institution_id", nativeQuery = true)
        List<UserInstitutionEntity> getByInstitutionId(@Param("institution_id") Long institution_id);

        // --------------------------DELETE--------------------------
        @Query(value = "DELETE FROM person_institution WHERE person_institution_id = :user_institution_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("user_institution_id") Long user_institution_id);

}
