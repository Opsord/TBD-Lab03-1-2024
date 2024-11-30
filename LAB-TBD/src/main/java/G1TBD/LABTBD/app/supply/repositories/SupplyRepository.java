package G1TBD.LABTBD.app.supply.repositories;

import G1TBD.LABTBD.app.supply.entities.SupplyEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends CrudRepository<SupplyEntity, Long> {

    // --------------------------CREATE--------------------------

    @Query(value = "INSERT INTO supply (name, description, classification) " +
            "VALUES (:name, :description, :classification)", nativeQuery = true)
    @Modifying
    @Transactional
    void create(@Param("name") String name,
                @Param("description") String description,
                @Param("classification") String classification);

    // ---------------------------READ---------------------------

    @Query(value = "SELECT * FROM supply", nativeQuery = true)
    List<SupplyEntity> getAll();

    @Query(value = "SELECT * FROM supply WHERE supply_id = :supply_id", nativeQuery = true)
    SupplyEntity getById(@Param("supply_id") Long supply_id);

    // --------------------------UPDATE--------------------------

    @Query(value = "UPDATE supply SET name = :name, description = :description, " +
            "classification = :classification WHERE supply_id = :supply_id", nativeQuery = true)
    @Modifying
    @Transactional
    void update(@Param("name") String name,
                @Param("description") String description,
                @Param("classification") String classification,
                @Param("supply_id") Long supply_id);

    // --------------------------DELETE--------------------------

    @Query(value = "DELETE FROM supply WHERE supply_id = :supply_id", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(@Param("supply_id") Long supply_id);

}
