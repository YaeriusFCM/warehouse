package warehouse.controller

import com.xlson.groovycsv.CsvParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

    static final String CSV_DATE_FORMAT = 'MM/dd/yy'

    @Value('${csv-input-file}') String CSV_INPUT_FILE

    @Autowired
    MetricsService metricsService

    @GetMapping(value = "/")
    def index() {
        [
            [url: '/', description: 'this info page'],
            [url: '/count', description: 'number of all elements stored in db'],
            [url: '/import', description: 'used only once to populate clean db with data from csv'],
            [url: '/campaigns', description: 'list all campaigns from db'],
            [url: '/campaigns/{search}', description: 'list all campaigns that contain <search> phrase in the name'],
            [url: '/datasources', description: 'list all datasources from db'],
            [url: '/datasources/{search}', description: 'list all datasources that contain <search> phrase in the name'],
            [url: '/cpmetrics/{campaignId}', description: 'show total metrics for a campaign chosen by id'],
            [url: '/cpmetrics/{campaignId}/{start}/{end}',
                description: 'show metrics for chosen campaign id within date range, <start> and <end> should be in yyyy-MM-dd format'],
            [url: '/cpmetricsovertime/{campaignId}', description: 'metrics over time for given campaign id'],
            [url: '/cpmetricsovertime/{campaignId}/{start}/{end}',
                description: 'metrics over time for given campaign id within date range, <start> and <end> should be in yyyy-MM-dd format'],
            [url: '/dsmetrics/{datasourceId}', description: 'show total metrics for a datasource chosen by id'],
            [url: '/dsmetrics/{datasourceId}/{start}/{end}',
                description: 'show metrics for chosen datasource id within date range, <start> and <end> should be in yyyy-MM-dd format'],
            [url: '/dsmetricsovertime/{datasourceId}', description: 'metrics over time for given datasource id'],
            [url: '/dsmetricsovertime/{datasourceId}/{start}/{end}',
                description: 'metrics over time for given datasource id within date range, <start> and <end> should be in yyyy-MM-dd format'],
            [url: '/v2/api-docs', description: 'automatic swagger api documentation']
        ]
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

        def datasources = metricsService.getAllDatasourcesAssoc()
        def campaigns = metricsService.getAllCampaignsAssoc()
        def metrics = []

        def data = new CsvParser().parse(new FileReader(CSV_INPUT_FILE))

        for (line in data) {
            if (!datasources.containsKey(line.Datasource)) {
                datasources.put(line.Datasource, metricsService.saveDatasource(new Datasource(name: line.Datasource)))
            }
            if (!campaigns.containsKey(line.Campaign)) {
                campaigns.put(line.Campaign, metricsService.saveCampaign(new Campaign(name: line.Campaign)))
            }

            metrics.add(new DailyMetrics(
                datasource: datasources.get(line.Datasource),
                campaign: campaigns.get(line.Campaign),
                date: Date.parse(CSV_DATE_FORMAT, line.Daily),
                clicks: Integer.parseInt(line.Clicks),
                impressions: Integer.parseInt(line.Impressions)
            ))
        }

        metricsService.saveAllMetrics(metrics)

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

