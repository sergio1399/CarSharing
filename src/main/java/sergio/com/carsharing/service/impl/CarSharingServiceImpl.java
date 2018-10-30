package sergio.com.carsharing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sergio.com.carsharing.dao.AutoCustomerRepository;
import sergio.com.carsharing.dao.CustomerRepository;
import sergio.com.carsharing.service.CarSharingService;

@Service
public class CarSharingServiceImpl implements CarSharingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSharingServiceImpl.class);

    private CustomerRepository customerRepository;

    private AutoCustomerRepository autoCustomerRepository;

    public CarSharingServiceImpl(CustomerRepository customerRepository, AutoCustomerRepository autoCustomerRepository) {
        this.customerRepository = customerRepository;
        this.autoCustomerRepository = autoCustomerRepository;
    }

}
