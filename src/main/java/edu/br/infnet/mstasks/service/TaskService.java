package edu.br.infnet.mstasks.service;

import edu.br.infnet.mstasks.dto.TaskDTO;
import edu.br.infnet.mstasks.exception.TaskNotFoundException;
import edu.br.infnet.mstasks.model.Task;
import edu.br.infnet.mstasks.producer.TaskHistoryProducer;
import edu.br.infnet.mstasks.repository.TaskRepository;
import edu.br.infnet.mstasks.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskHistoryProducer taskHistoryProducer;

    Logger logger= LogManager.getLogger(TaskService.class);

    public List<TaskDTO> getAllTasks() {
        try{
            List<TaskDTO> tasks = taskRepository.findAll().stream()
                    .map(Task::toDTO)
                    .collect(Collectors.toList());
            logger.info("TaskService:getAllTasks: {}", Mapper.mapToJsonString(tasks) );
            return tasks;
        }
        catch (Exception e){
            logger.error("TaskService:getAllTasks: {}", e.getMessage());
            throw new RuntimeException("TaskService:getAllTasks: " + e.getMessage());
        }

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

        taskHistoryProducer.logCreateAction(savedTask.getId(), savedTask.getUserId(), savedTask.toString());
        return savedTask.toDTO();
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        String oldValue = task.toString();

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        Task updatedTask = taskRepository.save(task);

        taskHistoryProducer.logUpdateAction(id, updatedTask.getUserId(), oldValue, updatedTask.toString());
        return updatedTask.toDTO();
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);
        taskHistoryProducer.logDeleteAction(id, task.getUserId(), task.toString());
    }
}
