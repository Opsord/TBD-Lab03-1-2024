package G1TBD.LABTBD.repositories;

import G1TBD.LABTBD.entities.TaskTTypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskTTypeRepository extends CrudRepository<TaskTTypeEntity, Long> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO task_ttype (task_id, ttype_id) " +
                        "VALUES (:task_id, :ttype_id)", nativeQuery = true)
        @Transactional
        @Modifying
        void create(@Param("task_id") Long task_id, @Param("ttype_id") Long ttype_id);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM task_ttype", nativeQuery = true)
        List<TaskTTypeEntity> getAll();

        @Query(value = "SELECT * FROM task_ttype " +
                        "WHERE task_ttype_id = :task_ttype_id", nativeQuery = true)
        TaskTTypeEntity getById(@Param("task_ttype_id") Long task_ttype_id);

        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE task_ttype SET task_id = :task_id, ttype_id = :ttype_id " +
                        "WHERE task_ttype_id = :task_ttype_id", nativeQuery = true)
        @Transactional
        @Modifying
        void update(@Param("task_ttype_id") Long task_ttype_id,
                        @Param("task_id") Long task_id,
                        @Param("ttype_id") Long ttype_id);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM task_ttype " +
                        "WHERE task_ttype_id = :task_ttype_id", nativeQuery = true)
        @Transactional
        @Modifying
        void delete(@Param("task_ttype_id") Long task_ttype_id);

}
