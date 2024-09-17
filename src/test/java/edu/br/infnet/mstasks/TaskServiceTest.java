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
import org.springframework.dao.DataAccessException;

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

    private Task mockTask;
    private TaskDTO mockTaskDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockTask = new Task();
        mockTask.setId(1L);
        mockTask.setTitle("Test Task");
        mockTask.setDescription("Task Description");
        mockTask.setUserId(1L);

        mockTaskDTO = new TaskDTO();
        mockTaskDTO.setId(1L);
        mockTaskDTO.setTitle("Test Task");
        mockTaskDTO.setDescription("Task Description");
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(mockTask));

        List<TaskDTO> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_Found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        TaskDTO taskDTO = taskService.getTaskById(1L);

        assertNotNull(taskDTO);
        assertEquals("Test Task", taskDTO.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        TaskDTO createdTask = taskService.createTask(mockTaskDTO);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskHistoryProducer, times(1)).logCreateAction(anyLong(), anyLong(), anyString());
    }

    @Test
    void testUpdateTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        TaskDTO updatedTask = taskService.updateTask(1L, mockTaskDTO);

        assertNotNull(updatedTask);
        assertEquals("Test Task", updatedTask.getTitle());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskHistoryProducer, times(1)).logUpdateAction(anyLong(), anyLong(), anyString(), anyString());
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, mockTaskDTO));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(any(Task.class));
        verify(taskHistoryProducer, times(1)).logDeleteAction(anyLong(), anyLong(), anyString());
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).findById(1L);
    }
}