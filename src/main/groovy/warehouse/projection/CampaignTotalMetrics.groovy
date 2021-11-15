package warehouse.projection

interface CampaignTotalMetrics {
    String getCampaign()
    int getClicks()
    int getImpressions()
    double getCtr()
}