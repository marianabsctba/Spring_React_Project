package edu.br.infnet.mstasks;

import edu.br.infnet.mstasks.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskDTOTest {

    @Test
    void taskDTO_shouldSetAndGetFieldsCorrectly() {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(1L);
        taskDTO.setTitle("Test Title");
        taskDTO.setDescription("Test Description");
        taskDTO.setUserId(2L);

        assertEquals(1L, taskDTO.getId());
        assertEquals("Test Title", taskDTO.getTitle());
        assertEquals("Test Description", taskDTO.getDescription());
        assertEquals(2L, taskDTO.getUserId());
    }

    @Test
    void taskDTO_shouldHaveDefaultConstructor() {
        TaskDTO taskDTO = new TaskDTO();
        assertNotNull(taskDTO);
    }
}
