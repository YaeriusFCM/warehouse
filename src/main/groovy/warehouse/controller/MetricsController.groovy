package warehouse.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import warehouse.model.Campaign
import warehouse.model.Datasource
import warehouse.service.MetricsService

/**
 * @author p.dobrzanski@yaerius.eu
 * Metrics controller processing more complex http requests
 * Uses MetricsService to fetch data
 */

@RestController
class MetricsController {

    static final String URL_DATE_FORMAT = 'yyyy-MM-dd'

    @Autowired
    MetricsService metricsService

    @GetMapping(value = "/cpmetrics/{campaignId}")
    def campaignMetrics(@PathVariable Long campaignId) {
        metrics(Campaign, campaignId)
    }

    @GetMapping(value = "/cpmetrics/{campaignId}/{strFrom}/{strUpto}")
    def campaignMetricsBetween(@PathVariable Long campaignId, @PathVariable String strFrom, @PathVariable String strUpto) {
        metrics(Campaign, campaignId, strFrom, strUpto)
    }

    @GetMapping(value = "/dsmetrics/{datasourceId}")
    def datasourceMetrics(@PathVariable Long datasourceId) {
        metrics(Datasource, datasourceId)
    }

    @GetMapping(value = "/dsmetrics/{datasourceId}/{strFrom}/{strUpto}")
    def datasourceMetricsBetween(@PathVariable Long datasourceId, @PathVariable String strFrom, @PathVariable String strUpto) {
        metrics(Datasource, datasourceId, strFrom, strUpto)
    }

    /**
     * Generic metrics method to avoid code duplication
     */
    def metrics(Class clazz, Long id, String strFrom = null, String strUpto = null) {
        Date from = strFrom ? Date.parse(URL_DATE_FORMAT, strFrom) : null
        Date upto = strUpto ? Date.parse(URL_DATE_FORMAT, strUpto) : null

        metricsService."getTotalMetricsFor$clazz.simpleName"(id, from, upto)
    }
}

