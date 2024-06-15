package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.TTypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TTypeRepository extends CrudRepository<TTypeEntity, Long> {

    // --------------------------CREATE--------------------------

    @Query(value = "INSERT INTO ttype (type) VALUES (:type)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(String name);

    // ---------------------------READ---------------------------

    @Query(value = "SELECT * FROM ttype", nativeQuery = true)
    List<TTypeEntity> getAll();

    @Query(value = "SELECT * FROM ttype WHERE ttype_id = :ttype_id", nativeQuery = true)
    TTypeEntity getById(Long ttype_id);

    // --------------------------UPDATE--------------------------

    @Query(value = "UPDATE ttype SET type = :type WHERE ttype_id = :ttype_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(Long ttype_id, String type);

    // --------------------------DELETE--------------------------

    @Query(value = "DELETE FROM ttype WHERE ttype_id = :ttype_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(Long ttype_id);

}
