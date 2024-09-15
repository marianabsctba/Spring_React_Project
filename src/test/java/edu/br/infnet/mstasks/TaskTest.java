package edu.br.infnet.mstasks;


import edu.br.infnet.mstasks.dto.TaskDTO;
import edu.br.infnet.mstasks.model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void toDTO_shouldConvertTaskToTaskDTO() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Task description");
        task.setUserId(2L);

        TaskDTO taskDTO = task.toDTO();

        assertEquals(task.getId(), taskDTO.getId());
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getUserId(), taskDTO.getUserId());
    }

    @Test
    void fromDTO_shouldConvertTaskDTOToTask() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Task description");
        taskDTO.setUserId(2L);

        Task task = Task.fromDTO(taskDTO);

        assertNull(task.getId());
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getDescription(), task.getDescription());
        assertEquals(taskDTO.getUserId(), task.getUserId());
    }

    @Test
    void toString_shouldReturnCorrectStringRepresentation() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Task description");
        task.setUserId(2L);

        String expectedString = "Task {ID=1, Title='Test Task', Description='Task description', UserId=2}";
        assertEquals(expectedString, task.toString());
    }
}
