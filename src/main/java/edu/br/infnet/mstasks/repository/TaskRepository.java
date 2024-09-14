package edu.br.infnet.mstasks.repository;

import edu.br.infnet.mstasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query
    public List<Task> findByUserId(Long id);
}
