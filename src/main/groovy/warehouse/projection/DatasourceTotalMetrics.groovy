package warehouse.projection

interface DatasourceTotalMetrics {
    String getDatasource()
    int getClicks()
    int getImpressions()
}