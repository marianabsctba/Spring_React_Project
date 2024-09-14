package pb.marianabs.infnet.edu.pb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pb.marianabs.infnet.edu.pb.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query
    public List<Task> findByUserId(Long id);
}
