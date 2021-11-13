package warehouse.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import warehouse.model.Campaign

@Repository
@Transactional
interface CampaignRepo extends CrudRepository<Campaign, Long> {
    Optional<Campaign> findDatasourceById(Long id)
    Optional<Campaign> findDatasourceByName(String name)
}
