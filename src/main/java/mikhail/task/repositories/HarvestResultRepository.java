package mikhail.task.repositories;

import mikhail.task.models.HarvestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestResultRepository extends JpaRepository<HarvestResult, Integer> {
    List<HarvestResult> findAllByUserId(int id);
}
