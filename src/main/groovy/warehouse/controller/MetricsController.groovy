package warehouse.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
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
        metricsService.getTotalMetricsForCampaign(campaignId)
    }

    @GetMapping(value = "/cpmetrics/{campaignId}/{strFrom}/{strUpto}")
    def campaignMetricsBetween(@PathVariable Long campaignId, @PathVariable String strFrom, @PathVariable String strUpto) {
        Date from = Date.parse(URL_DATE_FORMAT, strFrom)
        Date upto = Date.parse(URL_DATE_FORMAT, strUpto)

        metricsService.getTotalMetricsForCampaign(campaignId, from, upto)
    }
}

