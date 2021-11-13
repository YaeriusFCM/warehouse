package warehouse.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = 'daily_metrics')
class DailyMetrics {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id long id

    @Column Datasource datasource
    @Column Campaign campaign
    @Column Date date
    @Column int clicks
    @Column int impressions
}
