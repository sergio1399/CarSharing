package sergio.com.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import sergio.com.carsharing.model.AutoCustomer;

public interface AutoCustomerRepository extends CrudRepository<AutoCustomer, Long> {
}
