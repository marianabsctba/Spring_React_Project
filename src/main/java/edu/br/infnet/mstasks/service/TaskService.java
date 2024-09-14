package edu.br.infnet.mstasks.service;

import edu.br.infnet.mstasks.dto.TaskDTO;
import edu.br.infnet.mstasks.exception.TaskNotFoundException;
import edu.br.infnet.mstasks.model.Task;
import edu.br.infnet.mstasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskHistoryService taskHistoryService;

    //TODO: Quando criarmos a classe usuario precisaremos alterar a propriedade
    private String username = "User";

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return task.toDTO();
    }

    public List<TaskDTO> getTaskByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = Task.fromDTO(taskDTO);
        Task savedTask = taskRepository.save(task);

        // Registrar a criação no histórico
        taskHistoryService.logCreateAction(savedTask.getId(), username, savedTask.toString());
        return savedTask.toDTO();
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        String oldValue = task.toString();

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        Task updatedTask = taskRepository.save(task);

        // Registrar o estado anterior e a atualização no histórico
        taskHistoryService.logUpdateAction(id, username, oldValue, updatedTask.toString());
        return updatedTask.toDTO();
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        // Registrar a exclusão no histórico
        taskHistoryService.logDeleteAction(id, username, task.toString());

        taskRepository.delete(task);
    }
}
