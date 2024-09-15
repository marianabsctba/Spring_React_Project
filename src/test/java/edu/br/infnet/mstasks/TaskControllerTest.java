import edu.br.infnet.mstasks.controller.TaskController;
import edu.br.infnet.mstasks.dto.TaskDTO;
import edu.br.infnet.mstasks.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_shouldReturnListOfTasks() {
        List<TaskDTO> tasks = Arrays.asList(new TaskDTO(), new TaskDTO());
        when(taskService.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<TaskDTO>> response = taskController.getAllTasks();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void getTaskById_shouldReturnTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        when(taskService.getTaskById(1L)).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.getTaskById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void createTask_shouldReturnCreatedTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("New Task");
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("New Task", response.getBody().getTitle());
        verify(taskService, times(1)).createTask(any(TaskDTO.class));
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Updated Task");
        when(taskService.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(taskDTO);

        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, taskDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Task", response.getBody().getTitle());
        verify(taskService, times(1)).updateTask(eq(1L), any(TaskDTO.class));
    }

    @Test
    void deleteTask_shouldReturnNoContent() {
        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(taskService, times(1)).deleteTask(1L);
    }
}
