package warehouse.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = 'campaign')
class Campaign {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id long id

    @Column String name
}
