package warehouse.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import warehouse.model.Datasource

@Repository
@Transactional
interface DatasourceRepo extends CrudRepository<Datasource, Long> {
    Optional<Datasource> findDatasourceById(Long id)
    Optional<Datasource> findDatasourceByName(String name)
}
