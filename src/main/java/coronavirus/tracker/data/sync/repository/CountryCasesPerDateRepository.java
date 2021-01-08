package coronavirus.tracker.data.sync.repository;

import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryCasesPerDateRepository extends JpaRepository<CountryCasesPerDate, Long> {

}
