package warehouse.controller

import com.xlson.groovycsv.CsvParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import warehouse.model.Campaign
import warehouse.model.DailyMetrics
import warehouse.model.Datasource
import warehouse.service.MetricsService

/**
 * @author p.dobrzanski@yaerius.eu
 * Main controller processing basic http requests
 * Uses MetricsService to fetch data
 */

@RestController
class MainController {

    static final String CSV_INPUT_FILE = 'src/main/resources/cropped.csv'
    static final String CSV_DATE_FORMAT = 'MM/dd/yy'

    @Autowired
    MetricsService metricsService

    @GetMapping(value = "/")
    def index() {
        "hello"
    }

    @GetMapping(value = "/count")
    def count() {
        [
            'datasources': metricsService.countAllDatasources(),
            'campaigns': metricsService.countAllCampaigns(),
            'metrics': metricsService.countAllMetrics()
        ]
    }

    @GetMapping(value = "/import")
    def importData() {
        // to save from double data import
        def metricsCount = metricsService.countAllMetrics()
        if (metricsCount > 0) {
            return [
                'message': "metrics already imported, skipping",
                'metricsImported': metricsCount
            ]
        }

        def datasources = [:]
        def campaigns = [:]

        def data = new CsvParser().parse(new FileReader(CSV_INPUT_FILE))

        for (line in data) {
            if (!datasources.containsKey(line.Datasource)) {
                def datasource = metricsService.getDatasourceByName(line.Datasource)
                if (datasource.isPresent()) {
                    datasources.put(datasource.get().name, datasource.get())
                }
                else {
                    datasources.put(line.Datasource, metricsService.saveDatasource(new Datasource(name: line.Datasource)))
                }
            }
            if (!campaigns.containsKey(line.Campaign)) {
                def campaign = metricsService.getCampaignByName(line.Campaign)
                if (campaign.isPresent()) {
                    campaigns.put(campaign.get().name, campaign.get())
                }
                else {
                    campaigns.put(line.Campaign, metricsService.saveCampaign(new Campaign(name: line.Campaign)))
                }
            }

            metricsService.saveMetrics(new DailyMetrics(
                datasource: datasources.get(line.Datasource),
                campaign: campaigns.get(line.Campaign),
                date: Date.parse(CSV_DATE_FORMAT, line.Daily),
                clicks: Integer.parseInt(line.Clicks),
                impressions: Integer.parseInt(line.Impressions)
            ))
        }
        ['metricsImported': metricsService.countAllMetrics()]
    }

    @GetMapping(value = "/campaigns")
    def campaigns() {
        metricsService.getAllCampaigns()
    }

    @GetMapping(value = "/campaigns/{search}")
    def campaignsLike(@PathVariable String search) {
        metricsService.getCampaignsLike(search)
    }

    @GetMapping(value = "/datasources")
    def datasources() {
        metricsService.getAllDatasources()
    }

    @GetMapping(value = "/datasources/{search}")
    def datasourcesLike(@PathVariable String search) {
        metricsService.getDatasourcesLike(search)
    }
}

