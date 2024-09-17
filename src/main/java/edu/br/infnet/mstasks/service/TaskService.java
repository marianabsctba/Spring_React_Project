package edu.br.infnet.mstasks.service;

import edu.br.infnet.mstasks.dto.TaskDTO;
import edu.br.infnet.mstasks.exception.TaskNotFoundException;
import edu.br.infnet.mstasks.model.Task;
import edu.br.infnet.mstasks.producer.TaskHistoryProducer;
import edu.br.infnet.mstasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskHistoryProducer taskHistoryProducer;

    public List<TaskDTO> getAllTasks() {
        try {
            return taskRepository.findAll().stream()
                    .map(Task::toDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Error while fetching tasks.", e);
            throw new RuntimeException("Error while fetching tasks.", e);
        }
    }

    public TaskDTO getTaskById(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
            return task.toDTO();
        } catch (TaskNotFoundException e) {
            logger.error("Task with id {} not found.", id, e);
            throw e;
        } catch (DataAccessException e) {
            logger.error("Error while fetching task by id {}.", id, e);
            throw new RuntimeException("Error while fetching task by id.", e);
        }
    }

    public List<TaskDTO> getTaskByUserId(Long userId) {
        try {
            return taskRepository.findByUserId(userId).stream()
                    .map(Task::toDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            logger.error("Error while fetching tasks by user id {}.", userId, e);
            throw new RuntimeException("Error while fetching tasks by user id.", e);
        }
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        try {
            Task task = Task.fromDTO(taskDTO);
            Task savedTask = taskRepository.save(task);

            taskHistoryProducer.logCreateAction(savedTask.getId(), savedTask.getUserId(), savedTask.toString());
            return savedTask.toDTO();
        } catch (DataAccessException e) {
            logger.error("Error while creating task.", e);
            throw new RuntimeException("Error while creating task.", e);
        }
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDetails) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
            String oldValue = task.toString();

            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            Task updatedTask = taskRepository.save(task);

            taskHistoryProducer.logUpdateAction(id, updatedTask.getUserId(), oldValue, updatedTask.toString());
            return updatedTask.toDTO();
        } catch (TaskNotFoundException e) {
            logger.error("Task with id {} not found.", id, e);
            throw e;
        } catch (DataAccessException e) {
            logger.error("Error while updating task with id {}.", id, e);
            throw new RuntimeException("Error while updating task.", e);
        }
    }

    public void deleteTask(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

            taskRepository.delete(task);
            taskHistoryProducer.logDeleteAction(id, task.getUserId(), task.toString());
        } catch (TaskNotFoundException e) {
            logger.error("Task with id {} not found.", id, e);
            throw e;
        } catch (DataAccessException e) {
            logger.error("Error while deleting task with id {}.", id, e);
            throw new RuntimeException("Error while deleting task.", e);
        }
    }
}
