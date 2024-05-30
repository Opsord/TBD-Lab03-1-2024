package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.UserInstitutionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInstitutionRepository extends CrudRepository<UserInstitutionEntity, Long> {

        //--------------------------CREATE--------------------------
        @Query(value = "INSERT INTO user_institution (rut, institution) " +
                "VALUES (:rut, :institution)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("rut") String rut, @Param("institution") long institution);


        //--------------------------UPDATE--------------------------
        @Query(value = "UPDATE user_institution SET rut = :rut, institution = :institution " +
                "WHERE user_institution_id = :user_institution_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("user_institution_id") long user_institution_id, @Param("rut") String rut,
                    @Param("institution") long institution);


        //---------------------------READ---------------------------
        @Query(value = "SELECT * FROM user_institution", nativeQuery = true)
        List<UserInstitutionEntity> getAll();

        @Query(value = "SELECT * FROM user_institution WHERE user_institution_id = :user_institution_id", nativeQuery = true)
        UserInstitutionEntity getById(@Param("user_institution_id") long user_institution_id);

        @Query(value = "SELECT * FROM user_institution WHERE rut = :rut", nativeQuery = true)
        UserInstitutionEntity getByRut(@Param("rut") String rut);

        @Query(value = "SELECT * FROM user_institution WHERE institution = :institution", nativeQuery = true)
        List<UserInstitutionEntity> getByInstitutionId(@Param("institution") long institution);


        //--------------------------DELETE--------------------------
        @Query(value = "DELETE FROM user_institution WHERE user_institution_id = :user_institution_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("user_institution_id") long user_institution_id);



}
