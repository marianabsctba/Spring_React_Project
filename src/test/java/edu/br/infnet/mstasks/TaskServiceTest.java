package edu.br.infnet.mstasks;

import edu.br.infnet.mstasks.dto.TaskDTO;
import edu.br.infnet.mstasks.exception.TaskNotFoundException;
import edu.br.infnet.mstasks.model.Task;
import edu.br.infnet.mstasks.producer.TaskHistoryProducer;
import edu.br.infnet.mstasks.repository.TaskRepository;
import edu.br.infnet.mstasks.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskHistoryProducer taskHistoryProducer;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_shouldReturnListOfTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_shouldReturnTask() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskById(1L);

        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_shouldThrowTaskNotFoundException_whenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskByUserId_shouldReturnListOfTasksForUser() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.findByUserId(1L)).thenReturn(tasks);

        List<TaskDTO> result = taskService.getTaskByUserId(1L);

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findByUserId(1L);
    }

    @Test
    void updateTask_shouldUpdateTaskAndLogUpdateAction() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        TaskDTO updatedTaskDTO = new TaskDTO();
        updatedTaskDTO.setTitle("Updated Task");

        TaskDTO result = taskService.updateTask(1L, updatedTaskDTO);

        assertEquals("Updated Task", result.getTitle());
        verify(taskRepository, times(1)).save(existingTask);
        verify(taskHistoryProducer, times(1)).logUpdateAction(anyLong(), anyLong(), anyString(), anyString());
    }

    @Test
    void updateTask_shouldThrowTaskNotFoundException_whenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskDTO taskDTO = new TaskDTO();
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, taskDTO));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTask_shouldDeleteTaskAndLogDeleteAction() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
        verify(taskHistoryProducer, times(1)).logDeleteAction(anyLong(), anyLong(), anyString());
    }

    @Test
    void deleteTask_shouldThrowTaskNotFoundException_whenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).findById(1L);
    }
}
