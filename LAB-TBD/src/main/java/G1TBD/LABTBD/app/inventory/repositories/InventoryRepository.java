package G1TBD.LABTBD.app.inventory.repositories;

import G1TBD.LABTBD.app.inventory.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    // --------------------------CREATE--------------------------

    InventoryEntity save(InventoryEntity inventoryEntity);

    // ---------------------------READ---------------------------

    List<InventoryEntity> findAll();

    Optional<InventoryEntity> findById(Long inventoryId);

    // --------------------------UPDATE--------------------------

    InventoryEntity saveAndFlush(InventoryEntity inventoryEntity);

    // --------------------------DELETE--------------------------

    void deleteById(Long inventoryId);

}
