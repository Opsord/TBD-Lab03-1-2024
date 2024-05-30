package G1TBD.LABTBD.controllers;

import G1TBD.LABTBD.entities.TaskEntity;
import G1TBD.LABTBD.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;
    private static final Logger logger = Logger.getLogger(TaskController.class.getName());

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    String homeLinkRedirect = "redirect:/tasks";

    //--------------------------CREATE--------------------------
    @PostMapping("/create")
    public String create(@RequestBody TaskEntity task) {
        taskService.create(task);
        logger.info("Task created: ");
        logger.info(task.toString());
        return homeLinkRedirect;
    }


    //--------------------------UPDATE--------------------------
    @PutMapping("/update")
    public String update(@RequestBody TaskEntity task) {
        taskService.update(task);
        logger.info("Task updated: ");
        logger.info(task.toString());
        return homeLinkRedirect;
    }


    //---------------------------READ---------------------------
    @GetMapping("/all")
    public List<TaskEntity> getAll() {return taskService.getAll();}

    @GetMapping("/id/{id}")
    public TaskEntity getById(@PathVariable long id) {return taskService.getById(id);}

    @GetMapping("/emergency_id/{id}")
    public List<TaskEntity> getByemergency_id(@PathVariable long id) {return taskService.getByEmergencyId(id);}


    //--------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        taskService.delete(id);
        logger.info("Task deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }

}
