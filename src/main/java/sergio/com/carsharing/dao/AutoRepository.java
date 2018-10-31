package sergio.com.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import sergio.com.carsharing.model.Auto;

import java.util.Optional;

public interface AutoRepository extends CrudRepository<Auto, Long> {

    Optional<Auto> findByVin(String vin);
}
