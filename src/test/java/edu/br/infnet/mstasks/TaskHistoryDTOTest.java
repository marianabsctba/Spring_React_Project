package edu.br.infnet.mstasks;

import edu.br.infnet.mstasks.dto.TaskHistoryDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskHistoryDTOTest {

    @Test
    void taskHistoryDTO_shouldSetAndGetFieldsCorrectly() {
        TaskHistoryDTO taskHistoryDTO = new TaskHistoryDTO();

        taskHistoryDTO.setTaskId(1L);
        taskHistoryDTO.setUserId(2L);
        taskHistoryDTO.setChangeType("Update");
        taskHistoryDTO.setOldValue("Old Value");
        taskHistoryDTO.setNewValue("New Value");

        assertEquals(1L, taskHistoryDTO.getTaskId());
        assertEquals(2L, taskHistoryDTO.getUserId());
        assertEquals("Update", taskHistoryDTO.getChangeType());
        assertEquals("Old Value", taskHistoryDTO.getOldValue());
        assertEquals("New Value", taskHistoryDTO.getNewValue());
    }

    @Test
    void taskHistoryDTO_shouldHaveDefaultConstructor() {
        TaskHistoryDTO taskHistoryDTO = new TaskHistoryDTO();
        assertNotNull(taskHistoryDTO);
    }
}
