package edu.br.infnet.mstasks.controller;

import edu.br.infnet.mstasks.dto.TaskDTO;
import edu.br.infnet.mstasks.model.TaskHistory;
import edu.br.infnet.mstasks.service.TaskHistoryService;
import edu.br.infnet.mstasks.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskHistoryService taskHistoryService;

    public TaskController(TaskService taskService, TaskHistoryService taskHistoryService) {
        this.taskService = taskService;
        this.taskHistoryService = taskHistoryService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskDTO> userTasks = taskService.getTaskByUserId(userId);
        return ResponseEntity.ok(userTasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        TaskDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask( @RequestBody TaskDTO task) {
        return new ResponseEntity<>(taskService.createTask(task),HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDetails) {
        TaskDTO updatedTask = taskService.updateTask(taskId, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<TaskHistory>> getAllTaskHistory() {
        List<TaskHistory> taskHistory = taskHistoryService.getAllTaskHistory();
        return ResponseEntity.ok(taskHistory);
    }
}
