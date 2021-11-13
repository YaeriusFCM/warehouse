package warehouse.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import warehouse.model.Campaign
import warehouse.model.DailyMetrics
import warehouse.model.Datasource
import warehouse.repository.CampaignRepo
import warehouse.repository.DailyMetricsRepo
import warehouse.repository.DatasourceRepo

@Service
class MetricsService {

    @Autowired CampaignRepo campaignRepo
    @Autowired DatasourceRepo datasourceRepo
    @Autowired DailyMetricsRepo dailyMetricsRepo

    List<Campaign> getAllCampaigns() {
        campaignRepo.findAll()
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

    List<Datasource> getAllDatasources() {
        datasourceRepo.findAll()
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

    DailyMetrics saveMetrics(DailyMetrics m) {
        dailyMetricsRepo.save(m)
    }

    long countAllMetrics() {
        dailyMetricsRepo.count()
    }

}
