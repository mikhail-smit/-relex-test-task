package mikhail.task.repositories;

import mikhail.task.models.HarvestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HarvestResultRepository extends JpaRepository<HarvestResult, Integer> {
    List<HarvestResult> findAllByUserId(int id);

    List<HarvestResult> findAllByAtMomentBetween(Date from, Date to);
    List<HarvestResult> findAllByAtMomentBetweenAndUserId(Date from, Date to, int id);
}
