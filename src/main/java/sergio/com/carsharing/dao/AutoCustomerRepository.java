package sergio.com.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import sergio.com.carsharing.model.AutoCustomer;

import java.util.Optional;

public interface AutoCustomerRepository extends CrudRepository<AutoCustomer, Long> {
    @Override
    Optional<AutoCustomer> findById(Long aLong);

    @Override
    <S extends AutoCustomer> S save(S s);

    Optional<AutoCustomer> findByAuto_VinAndCustomer_PassportNumberAndStatus(String vin, String passportNumber, String status);

    Optional<AutoCustomer> findByAuto_VinAndStatus(String vin, String status);

}
