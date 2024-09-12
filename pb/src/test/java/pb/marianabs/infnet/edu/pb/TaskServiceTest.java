package pb.marianabs.infnet.edu.pb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pb.marianabs.infnet.edu.pb.dto.TaskDTO;
import pb.marianabs.infnet.edu.pb.model.Task;
import pb.marianabs.infnet.edu.pb.repository.TaskRepository;
import pb.marianabs.infnet.edu.pb.service.TaskHistoryService;
import pb.marianabs.infnet.edu.pb.service.TaskService;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskHistoryService taskHistoryService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Create Task");
        task.setDescription("Description Create Task");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO createdTask = taskService.createTask(task.toDTO());

        assertEquals("Create Task", createdTask.getTitle());
        verify(taskHistoryService, times(1)).logCreateAction(eq(1L), eq("User"), anyString());
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Existing Task");

        TaskDTO updatedDetails = new TaskDTO();
        updatedDetails.setTitle("Updated Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        TaskDTO updatedTask = taskService.updateTask(1L, updatedDetails);

        assertEquals("Updated Task", updatedTask.getTitle());
        verify(taskHistoryService, times(1)).logUpdateAction(eq(1L), eq("User"), anyString(), anyString());
    }

    @Test
    public void testDeleteTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Task to delete");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        taskService.deleteTask(1L);

        verify(taskHistoryService, times(1)).logDeleteAction(eq(1L), eq("User"), anyString());
        verify(taskRepository, times(1)).delete(existingTask);
    }
}

