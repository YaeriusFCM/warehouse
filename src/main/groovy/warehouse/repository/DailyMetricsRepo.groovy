package warehouse.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import warehouse.model.DailyMetrics
import warehouse.projection.CampaignTotalMetrics

@Repository
@Transactional
interface DailyMetricsRepo extends CrudRepository<DailyMetrics, Long> {
    Optional<DailyMetrics> findById(Long id)

    @Query(value = '''
            SELECT c.name AS campaign, SUM(m.clicks) AS clicks, SUM(m.impressions) AS impressions 
            FROM daily_metrics m 
            INNER JOIN campaign c ON m.campaign_id = c.id
            WHERE c.id = ?1
            GROUP BY c.name
        ''', nativeQuery = true)
    CampaignTotalMetrics getTotalMetricsForCampaignId(long campaignId)

    @Query(value = '''
            SELECT c.name AS campaign, SUM(m.clicks) AS clicks, SUM(m.impressions) AS impressions 
            FROM daily_metrics m 
            INNER JOIN campaign c ON m.campaign_id = c.id
            WHERE c.id = ?1 AND m.date >= ?2 AND m.date <= ?3
            GROUP BY c.name
        ''', nativeQuery = true)
    CampaignTotalMetrics getTotalMetricsForCampaignIdBetween(long campaignId, Date from, Date upto)
}
