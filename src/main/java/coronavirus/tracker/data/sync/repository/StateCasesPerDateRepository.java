package coronavirus.tracker.data.sync.repository;

import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateCasesPerDateRepository extends JpaRepository<StateCasesPerDate, Long> {

}
