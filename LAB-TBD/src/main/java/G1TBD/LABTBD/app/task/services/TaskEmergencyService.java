package G1TBD.LABTBD.app.task.services;

import G1TBD.LABTBD.app.task.entities.TaskEmergencyEntity;
import G1TBD.LABTBD.app.task.repositories.TaskEmergencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskEmergencyService {

    private final TaskEmergencyRepository taskEmergencyRepository;

    public TaskEmergencyService(TaskEmergencyRepository taskEmergencyRepository) {
        this.taskEmergencyRepository = taskEmergencyRepository;
    }

    // --------------------------CREATE--------------------------

    public void create(Long task_id, Long emergency_id) {
        taskEmergencyRepository.create(task_id, emergency_id);
    }

    // ---------------------------READ---------------------------

    public List<TaskEmergencyEntity> getAll() {
        return taskEmergencyRepository.getAll();
    }

    public TaskEmergencyEntity getById(Long task_emergency_id) {
        return taskEmergencyRepository.getById(task_emergency_id);
    }

    public List<TaskEmergencyEntity> getEmergenciesByTaskId(Long task_id) {
        return taskEmergencyRepository.getEmergenciesByTaskId(task_id);
    }

    public List<TaskEmergencyEntity> getTasksByEmergencyId(Long emergency_id) {
        return taskEmergencyRepository.getTasksByEmergencyId(emergency_id);
    }

    // --------------------------UPDATE--------------------------

    public void update(TaskEmergencyEntity taskEmergency) {
        taskEmergencyRepository.update(
                taskEmergency.getTask_emergency_id(),
                taskEmergency.getTask_id(),
                taskEmergency.getEmergency_id());
    }

    // --------------------------DELETE--------------------------

    public void delete(Long task_emergency_id) {
        taskEmergencyRepository.delete(task_emergency_id);
    }

}
