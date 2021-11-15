package warehouse.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import warehouse.model.Campaign
import warehouse.model.DailyMetrics
import warehouse.model.Datasource
import warehouse.repository.CampaignRepo
import warehouse.repository.DailyMetricsRepo
import warehouse.repository.DatasourceRepo

/**
 * @author p.dobrzanski@yaerius.eu
 * Serice class used to fetch all data related to metrics
 * Uses various repositories to grab data from particular db tables
 */

@Service
class MetricsService {

    @Autowired CampaignRepo campaignRepo
    @Autowired DatasourceRepo datasourceRepo
    @Autowired DailyMetricsRepo dailyMetricsRepo

    /*** CAMPAIGNS ***/

    List<Campaign> getAllCampaigns() {
        campaignRepo.findAll()
    }

    List<Campaign> getCampaignsLike(String search) {
        campaignRepo.findByNameContainingIgnoreCase(search)
    }

    Optional<Campaign> getCampaignByName(String name) {
        campaignRepo.findByName(name)
    }

    long countAllCampaigns() {
        campaignRepo.count()
    }

    Campaign saveCampaign(Campaign c) {
        campaignRepo.save(c)
    }

    /*** DATASOURCES ***/

    List<Datasource> getAllDatasources() {
        datasourceRepo.findAll()
    }

    List<Campaign> getDatasourcesLike(String search) {
        datasourceRepo.findByNameContainingIgnoreCase(search)
    }

    Optional<Datasource> getDatasourceByName(String name) {
        datasourceRepo.findByName(name)
    }

    Datasource saveDatasource(Datasource ds) {
        datasourceRepo.save(ds)
    }

    long countAllDatasources() {
        datasourceRepo.count()
    }

    /*** METRICS ***/

    DailyMetrics saveMetrics(DailyMetrics m) {
        dailyMetricsRepo.save(m)
    }

    long countAllMetrics() {
        dailyMetricsRepo.count()
    }

    def getTotalMetricsForCampaign(long campaignId, Date from = null, Date upto = null) {
        if (from && upto) {
            dailyMetricsRepo.getTotalMetricsForCampaignIdBetween(campaignId, from, upto)
        }
        else {
            dailyMetricsRepo.getTotalMetricsForCampaignId(campaignId)
        }
    }

    def getTotalMetricsForDatasource(long datasourceId, Date from = null, Date upto = null) {
        if (from && upto) {
            dailyMetricsRepo.getTotalMetricsForDatasourceIdBetween(datasourceId, from, upto)
        }
        else {
            dailyMetricsRepo.getTotalMetricsForDatasourceId(datasourceId)
        }
    }

}
