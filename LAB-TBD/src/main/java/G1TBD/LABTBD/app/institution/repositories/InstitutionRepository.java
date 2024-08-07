package G1TBD.LABTBD.app.institution.repositories;

import G1TBD.LABTBD.app.institution.entities.InstitutionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends CrudRepository<InstitutionEntity, Long> {

    // --------------------------CREATE--------------------------

    @Query(value = "INSERT INTO institution (name) VALUES (:name)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("name") String name);

    // ---------------------------READ---------------------------

    @Query(value = "SELECT * FROM institution", nativeQuery = true)
    List<InstitutionEntity> getAll();

    @Query(value = "SELECT * FROM institution WHERE institution_id = :institution_id", nativeQuery = true)
    InstitutionEntity getById(@Param("institution_id") Long institution_id);

    // --------------------------UPDATE--------------------------

    @Query(value = "UPDATE institution SET name = :name WHERE institution_id = :institution_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("institution_id") Long institution_id,
            @Param("name") String name);

    // --------------------------DELETE--------------------------

    @Query(value = "DELETE FROM institution WHERE institution_id = :institution_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("institution_id") Long institution_id);

}
