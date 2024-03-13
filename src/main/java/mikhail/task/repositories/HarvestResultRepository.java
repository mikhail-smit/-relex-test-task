package mikhail.task.repositories;

import mikhail.task.models.HarvestResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HarvestResultRepository extends CrudRepository<HarvestResult, Integer> {
    List<HarvestResult> findAllByUserId(int id);
}
