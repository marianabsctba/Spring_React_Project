package pb.marianabs.infnet.edu.pb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pb.marianabs.infnet.edu.pb.dto.TaskDTO;
import pb.marianabs.infnet.edu.pb.model.TaskHistory;
import pb.marianabs.infnet.edu.pb.service.TaskHistoryService;
import pb.marianabs.infnet.edu.pb.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskHistoryService taskHistoryService;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/user/{id}")
    public List<TaskDTO> getAllTasks(@PathVariable Long id) {
        return taskService.getTaskByUserId(id);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/history")
    public List<TaskHistory> getAllTaskHistory() {
        return taskHistoryService.getAllTaskHistory();
    }
}
