package pb.marianabs.infnet.edu.pb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pb.marianabs.infnet.edu.pb.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
