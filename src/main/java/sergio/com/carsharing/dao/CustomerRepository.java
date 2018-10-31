package sergio.com.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import sergio.com.carsharing.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByPassportNumber(String passportNumber);

    @Override
    <S extends Customer> S save(S s);
}
