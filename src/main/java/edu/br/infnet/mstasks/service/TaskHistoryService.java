package edu.br.infnet.mstasks.service;

import edu.br.infnet.mstasks.model.TaskHistory;
import edu.br.infnet.mstasks.repository.TaskHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskHistoryService {

    @Autowired
    private TaskHistoryRepository taskHistoryRepository;

    public void logCreateAction(Long taskId, String changedBy, String newValue) {
        TaskHistory history = new TaskHistory();
        history.setTaskId(taskId);
        history.setChangedBy(changedBy);
        history.setChangeType("CREATE");
        history.setChangeTimestamp(LocalDateTime.now());
        history.setNewValue(newValue);

        taskHistoryRepository.save(history);
    }

    public void logUpdateAction(Long taskId, String changedBy, String oldValue, String newValue) {
        TaskHistory history = new TaskHistory();
        history.setTaskId(taskId);
        history.setChangedBy(changedBy);
        history.setChangeType("UPDATE");
        history.setChangeTimestamp(LocalDateTime.now());
        history.setOldValue(oldValue);
        history.setNewValue(newValue);

        taskHistoryRepository.save(history);
    }

    public void logDeleteAction(Long taskId, String changedBy, String oldValue) {
        TaskHistory history = new TaskHistory();
        history.setTaskId(taskId);
        history.setChangedBy(changedBy);
        history.setChangeType("DELETE");
        history.setChangeTimestamp(LocalDateTime.now());
        history.setOldValue(oldValue);

        taskHistoryRepository.save(history);
    }

    public List<TaskHistory> getAllTaskHistory() {
        return taskHistoryRepository.findAll();
    }
}
