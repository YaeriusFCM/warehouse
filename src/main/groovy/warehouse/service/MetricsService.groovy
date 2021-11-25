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

    Map<String,Campaign> getAllCampaignsAssoc() {
        def assoc = [:]
        allCampaigns.each {c ->
            assoc.put(c.name, c)
        }
        assoc
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

    Map<String,Datasource> getAllDatasourcesAssoc() {
        def assoc = [:]
        allDatasources.each {ds ->
            assoc.put(ds.name, ds)
        }
        assoc
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

    List<DailyMetrics> saveAllMetrics(List<DailyMetrics> dmList) {
        dailyMetricsRepo.saveAll(dmList)
    }

    long countAllMetrics() {
        dailyMetricsRepo.count()
    }

    def getMetricsOverTimeForCampaign(long campaignId, Date from = null, Date upto = null) {
        if (from && upto) {
            dailyMetricsRepo.findByCampaignIdAndDateBetween(campaignId, from, upto)
        }
        else {
            dailyMetricsRepo.findByCampaignId(campaignId)
        }

    }

    def getTotalMetricsForCampaign(long campaignId, Date from = null, Date upto = null) {
        if (from && upto) {
            dailyMetricsRepo.getTotalMetricsForCampaignIdAndDateBetween(campaignId, from, upto)
        }
        else {
            dailyMetricsRepo.getTotalMetricsForCampaignId(campaignId)
        }
    }

    def getMetricsOverTimeForDatasource(long datasourceId, Date from = null, Date upto = null) {
        if (from && upto) {
            dailyMetricsRepo.findByDatasourceIdAndDateBetween(datasourceId, from, upto)
        }
        else {
            dailyMetricsRepo.findByDatasourceId(datasourceId)
        }

    }

    def getTotalMetricsForDatasource(long datasourceId, Date from = null, Date upto = null) {
        if (from && upto) {
            dailyMetricsRepo.getTotalMetricsForDatasourceIdAndDateBetween(datasourceId, from, upto)
        }
        else {
            dailyMetricsRepo.getTotalMetricsForDatasourceId(datasourceId)
        }
    }

}
