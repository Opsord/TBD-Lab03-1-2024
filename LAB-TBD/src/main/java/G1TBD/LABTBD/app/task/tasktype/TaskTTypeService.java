package G1TBD.LABTBD.app.task.tasktype;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTTypeService {

    private final TaskTTypeRepository taskTTypeRepository;

    public TaskTTypeService(TaskTTypeRepository taskTTypeRepository) {
        this.taskTTypeRepository = taskTTypeRepository;
    }

    // --------------------------CREATE--------------------------

    private void create(TaskTTypeEntity taskTType) {
        taskTTypeRepository.create(taskTType.getTask_id(), taskTType.getTtype_id());
    }

    // --------------------------READ----------------------------

    private List<TaskTTypeEntity> getAll() {
        return taskTTypeRepository.getAll();
    }

    private TaskTTypeEntity getById(Long id) {
        return taskTTypeRepository.getById(id);
    }

    // --------------------------UPDATE--------------------------

    private void update(TaskTTypeEntity taskTType) {
        taskTTypeRepository.update(taskTType.getTask_ttype_id(), taskTType.getTask_id(), taskTType.getTtype_id());
    }

    // --------------------------DELETE--------------------------

    private void delete(Long id) {
        taskTTypeRepository.delete(id);
    }

}
