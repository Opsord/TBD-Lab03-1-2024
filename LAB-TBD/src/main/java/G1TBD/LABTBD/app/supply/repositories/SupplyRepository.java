package G1TBD.LABTBD.app.supply.repositories;

import G1TBD.LABTBD.app.supply.entities.SupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SupplyRepository extends JpaRepository<SupplyEntity, Long> {

    // --------------------------CREATE--------------------------

    SupplyEntity save(SupplyEntity supplyEntity);

    // ---------------------------READ---------------------------

    List<SupplyEntity> findAll();

    Optional<SupplyEntity> findById(Long supplyId);

    // --------------------------UPDATE--------------------------

    SupplyEntity saveAndFlush(SupplyEntity supplyEntity);

    // --------------------------DELETE--------------------------

    void deleteById(Long supplyId);

}

