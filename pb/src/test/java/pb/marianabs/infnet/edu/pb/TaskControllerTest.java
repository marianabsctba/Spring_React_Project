package pb.marianabs.infnet.edu.pb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pb.marianabs.infnet.edu.pb.controller.TaskController;
import pb.marianabs.infnet.edu.pb.dto.TaskDTO;
import pb.marianabs.infnet.edu.pb.service.TaskHistoryService;
import pb.marianabs.infnet.edu.pb.service.TaskService;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskHistoryService taskHistoryService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetAllTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetTaskById() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setId(1L);
        task.setTitle("Sample Task");

        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Task"));
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setId(1L);
        task.setTitle("Create a new Task");

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content("{\"title\": \"Create a new Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Create a new Task"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskDTO task = new TaskDTO();
        task.setId(1L);
        task.setTitle("Updated Task");

        when(taskService.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType("application/json")
                        .content("{\"title\": \"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    public void testGetTaskHistory() throws Exception {
        when(taskHistoryService.getAllTaskHistory()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/tasks/history"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}

