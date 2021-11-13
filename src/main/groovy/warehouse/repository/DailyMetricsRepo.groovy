package warehouse.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import warehouse.model.DailyMetrics

@Repository
@Transactional
interface DailyMetricsRepo extends CrudRepository<DailyMetrics, Long> {
    Optional<DailyMetrics> findById(Long id)
}
