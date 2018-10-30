package sergio.com.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import sergio.com.carsharing.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
