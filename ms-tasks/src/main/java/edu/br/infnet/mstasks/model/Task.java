package edu.br.infnet.mstasks.model;

import edu.br.infnet.mstasks.dto.TaskDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long userId;


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getUserId() {
        return userId;
    }
    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    // Conversion methods
    public TaskDTO toDTO() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(this.id);
        taskDTO.setTitle(this.title);
        taskDTO.setDescription(this.description);
        taskDTO.setUserId(this.userId);
        return taskDTO;
    }

    public static Task fromDTO(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setUserId(taskDTO.getUserId());
        return task;
    }


    @Override
    public String toString() {
        return "Task {" +
                "ID=" + id +
                ", Title='" + title + '\'' +
                ", Description='" + description + '\'' +
                ", UserId=" + userId +
                '}';
    }
}
