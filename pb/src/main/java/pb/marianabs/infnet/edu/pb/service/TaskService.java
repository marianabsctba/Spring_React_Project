package pb.marianabs.infnet.edu.pb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pb.marianabs.infnet.edu.pb.dto.TaskDTO;
import pb.marianabs.infnet.edu.pb.exception.TaskNotFoundException;
import pb.marianabs.infnet.edu.pb.model.Task;
import pb.marianabs.infnet.edu.pb.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(Task::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return task.toDTO();
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = Task.fromDTO(taskDTO);
        return taskRepository.save(task).toDTO();
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        return taskRepository.save(task).toDTO();
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }
}
