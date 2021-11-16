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

    @GetMapping(value = "/cpmetricsovertime/{campaignId}")
    def campaignMetricsOverTime(@PathVariable Long campaignId) {
        metricsOverTime(Campaign, campaignId)
    }

    @GetMapping(value = "/cpmetricsovertime/{campaignId}/{strFrom}/{strUpto}")
    def campaignMetricsOverTimeBetween(@PathVariable Long campaignId, @PathVariable String strFrom, @PathVariable String strUpto) {
        metricsOverTime(Campaign, campaignId, strFrom, strUpto)
    }

    @GetMapping(value = "/cpmetrics/{campaignId}")
    def campaignMetrics(@PathVariable Long campaignId) {
        metricsTotal(Campaign, campaignId)
    }

    @GetMapping(value = "/cpmetrics/{campaignId}/{strFrom}/{strUpto}")
    def campaignMetricsBetween(@PathVariable Long campaignId, @PathVariable String strFrom, @PathVariable String strUpto) {
        metricsTotal(Campaign, campaignId, strFrom, strUpto)
    }

    @GetMapping(value = "/dsmetricsovertime/{datasourceId}")
    def datasourceMetricsOverTime(@PathVariable Long datasourceId) {
        metricsOverTime(Datasource, datasourceId)
    }

    @GetMapping(value = "/dsmetricsovertime/{datasourceId}/{strFrom}/{strUpto}")
    def datasourceMetricsOverTimeBetween(@PathVariable Long datasourceId, @PathVariable String strFrom, @PathVariable String strUpto) {
        metricsOverTime(Datasource, datasourceId, strFrom, strUpto)
    }

    @GetMapping(value = "/dsmetrics/{datasourceId}")
    def datasourceMetrics(@PathVariable Long datasourceId) {
        metricsTotal(Datasource, datasourceId)
    }

    @GetMapping(value = "/dsmetrics/{datasourceId}/{strFrom}/{strUpto}")
    def datasourceMetricsBetween(@PathVariable Long datasourceId, @PathVariable String strFrom, @PathVariable String strUpto) {
        metricsTotal(Datasource, datasourceId, strFrom, strUpto)
    }

    /**
     * Generic metrics methods to avoid code duplication
     */
    def metricsTotal(Class clazz, Long id, String strFrom = null, String strUpto = null) {
        Date from = strFrom ? Date.parse(URL_DATE_FORMAT, strFrom) : null
        Date upto = strUpto ? Date.parse(URL_DATE_FORMAT, strUpto) : null

        metricsService."getTotalMetricsFor$clazz.simpleName"(id, from, upto)
    }

    def metricsOverTime(Class clazz, Long id, String strFrom = null, String strUpto = null) {
        Date from = strFrom ? Date.parse(URL_DATE_FORMAT, strFrom) : null
        Date upto = strUpto ? Date.parse(URL_DATE_FORMAT, strUpto) : null

        metricsService."getMetricsOverTimeFor$clazz.simpleName"(id, from, upto)
    }
}

