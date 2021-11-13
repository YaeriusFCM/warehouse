package warehouse.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity(name = 'daily_metrics')
class DailyMetrics {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id long id

    @ManyToOne
    @JoinColumn(name = 'datasource_id')
    Datasource datasource

    @ManyToOne
    @JoinColumn(name = 'campaign_id')
    Campaign campaign

    @Column Date date
    @Column int clicks
    @Column int impressions
}
