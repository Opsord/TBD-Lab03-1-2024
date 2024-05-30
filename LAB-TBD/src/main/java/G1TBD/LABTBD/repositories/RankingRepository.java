package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.RankingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends CrudRepository<RankingEntity, Long> {

        //--------------------------CREATE--------------------------
        @Query(value = "INSERT INTO rankings (volunteer, task, value) " +
                "VALUES (:volunteer, :task, :value)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("volunteer") String volunteer,
                    @Param("task") long idtask, @Param("value") int value);


        //--------------------------UPDATE--------------------------
        @Query(value = "UPDATE rankings SET volunteer = :volunteer, task = :task, value = :value " +
                "WHERE ranking_id = :ranking_id", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("ranking_id") long ranking_id, @Param("volunteer") String volunteer,
                    @Param("task") long task, @Param("value") int value);


        //---------------------------READ---------------------------
        @Query(value = "SELECT * FROM rankings", nativeQuery = true)
        List<RankingEntity> getAll();

        @Query(value = "SELECT * FROM rankings WHERE ranking_id = :ranking_id", nativeQuery = true)
        RankingEntity getById(@Param("ranking_id") long ranking_id);

        @Query(value = "SELECT * FROM rankings WHERE task_id = :id", nativeQuery = true)
        List<RankingEntity> getByTaskId(@Param("id") long id);


        //--------------------------DELETE--------------------------
        @Query(value = "DELETE FROM rankings WHERE ranking_id = :ranking_id", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("ranking_id") long ranking_id);


}
