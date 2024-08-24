package pb.marianabs.infnet.edu.pb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pb.marianabs.infnet.edu.pb.model.TaskHistory;
import pb.marianabs.infnet.edu.pb.repository.TaskHistoryRepository;
import pb.marianabs.infnet.edu.pb.service.TaskHistoryService;

public class TaskHistoryServiceTest {

    @Mock
    private TaskHistoryRepository taskHistoryRepository;

    @InjectMocks
    private TaskHistoryService taskHistoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogCreateAction() {
        taskHistoryService.logCreateAction(1L, "UserA", "Task Data");

        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    public void testLogUpdateAction() {
        taskHistoryService.logUpdateAction(1L, "UserA", "Old Task Data", "New Task Data");

        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    public void testLogDeleteAction() {
        taskHistoryService.logDeleteAction(1L, "UserA", "Task Data");

        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }
}
