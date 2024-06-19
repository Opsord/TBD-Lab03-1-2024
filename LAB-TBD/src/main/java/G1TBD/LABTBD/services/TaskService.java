package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.TaskEmergencyEntity;
import G1TBD.LABTBD.entities.TaskEntity;
import G1TBD.LABTBD.entities.UserTaskEntity;
import G1TBD.LABTBD.entities.UserEntity;
import G1TBD.LABTBD.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserTaskService userTaskService;
    private final UserService userService;
    private final TaskEmergencyService taskEmergencyService;
    private static final Logger logger = Logger.getLogger(TaskService.class.getName());

    @Autowired
    public TaskService(TaskRepository taskRepository, UserTaskService userTaskService,
                       TaskEmergencyService taskEmergencyService, UserService userService) {
        this.taskRepository = taskRepository;
        this.userTaskService = userTaskService;
        this.userService = userService;
        this.taskEmergencyService = taskEmergencyService;
    }

    // --------------------------CREATE--------------------------

    public void create(TaskEntity task) {
        taskRepository.create(
                task.getDescription(),
                task.isStatus());
        logger.info("Task created");
    }

    // ---------------------------READ---------------------------

    public List<TaskEntity> getAll() {
        return taskRepository.getAll();
    }

    public TaskEntity getById(Long id) {
        return taskRepository.getById(id);
    }

    public List<TaskEntity> getTasksByEmergencyId(Long id) {
        List<TaskEmergencyEntity> taskEmergencyEntities = taskEmergencyService.getTasksByEmergencyId(id);
        List<TaskEntity> tasks = new ArrayList<>();
        for (TaskEmergencyEntity taskEmergencyEntity : taskEmergencyEntities) {
            tasks.add(getById(taskEmergencyEntity.getTask_id()));
        }
        return tasks;
    }

    public List<UserEntity> getVolunteersByTask(Long task_id) {
        List<UserTaskEntity> taskUserEntities = userTaskService.getVolunteersByTaskId(task_id);
        List<UserEntity> volunteers = new ArrayList<>();
        for (UserTaskEntity taskUserEntity : taskUserEntities) {
            volunteers.add(userService.getByRut(taskUserEntity.getRut()));
        }
        return volunteers;
    }

    public List<TaskEntity> getTasksByVolunteer(String volunteer) {
        List<UserTaskEntity> taskUserEntities = userTaskService.getTasksByVolunteer(volunteer);
        List<TaskEntity> tasks = new ArrayList<>();
        for (UserTaskEntity taskUserEntity : taskUserEntities) {
            tasks.add(getById(taskUserEntity.getTask_id()));
        }
        return tasks;
    }

    // --------------------------UPDATE--------------------------

    public void update(TaskEntity task) {
        taskRepository.update(
                task.getTask_id(),
                task.getDescription(),
                task.isStatus());
        logger.info("Task updated");
    }

    // --------------------------DELETE--------------------------
    public void delete(Long id) {
        taskRepository.delete(id);
        logger.info("Task deleted");
    }

}
