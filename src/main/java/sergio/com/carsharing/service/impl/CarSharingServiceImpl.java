package sergio.com.carsharing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sergio.com.carsharing.dao.AutoCustomerRepository;
import sergio.com.carsharing.dao.AutoRepository;
import sergio.com.carsharing.dao.CustomerRepository;
import sergio.com.carsharing.dto.AutoDto;
import sergio.com.carsharing.dto.RentDto;
import sergio.com.carsharing.dto.enums.RentStatus;
import sergio.com.carsharing.exception.CarSharingServiceException;
import sergio.com.carsharing.model.Auto;
import sergio.com.carsharing.model.AutoCustomer;
import sergio.com.carsharing.model.Customer;
import sergio.com.carsharing.service.CarSharingService;
import sergio.com.carsharing.service.CheckService;
import sergio.com.carsharing.utils.AutoCustomerRentConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarSharingServiceImpl implements CarSharingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSharingServiceImpl.class);

    private static final String RIGHT_STATUS = "active";

    private CheckService checkService;

    private AutoRepository autoRepository;

    private CustomerRepository customerRepository;

    private AutoCustomerRepository autoCustomerRepository;

    public CarSharingServiceImpl(CheckService checkService,
                                 AutoRepository autoRepository,
                                 CustomerRepository customerRepository,
                                 AutoCustomerRepository autoCustomerRepository) {
        this.checkService = checkService;
        this.autoRepository = autoRepository;
        this.customerRepository = customerRepository;
        this.autoCustomerRepository = autoCustomerRepository;
    }

    @Override
    public String closeRent(String vin, String passportNumber) throws CarSharingServiceException {
        Optional<AutoCustomer> autoCustomer =
                autoCustomerRepository.
                        findByAuto_VinAndCustomer_PassportNumberAndStatus(vin,
                                                                          passportNumber,
                                                                          RIGHT_STATUS);
        String status = closeRentProcess(autoCustomer);
        autoCustomerRepository.save(autoCustomer.get());
        return status;
    }

    @Override
    @Transactional
    public String closeRent(Long id) throws CarSharingServiceException {
        Optional<AutoCustomer> autoCustomer = autoCustomerRepository.findById(id);
        return closeRentProcess(autoCustomer);
    }

    @Override
    @Transactional
    public String openRent(RentDto rentDto) throws CarSharingServiceException {
        AutoCustomer autoCustomer = AutoCustomerRentConverter.convertToModel(rentDto);
        Optional<Auto> persistedAuto = autoRepository.findByVin(autoCustomer.getAuto().getVin().trim());
        if (!persistedAuto.isPresent()) {
            LOGGER.error("No auto records found!");
            throw new CarSharingServiceException("Автомобиль не найден в БД!");
        }
        Optional<AutoCustomer> checkAnotherRent = autoCustomerRepository.
                findByAuto_VinAndStatus(persistedAuto.get().getVin(), RIGHT_STATUS);
        if (checkAnotherRent.isPresent()) {
            LOGGER.error("The car is occupied!");
            throw new CarSharingServiceException("Данный момент автомобиль уже находится в аренде!");
        }
        Optional<Customer> persistedCustomer = customerRepository.findByPassportNumber(autoCustomer.getCustomer().getPassportNumber().trim());
        if (!persistedCustomer.isPresent()) {
           Customer newCustomer = customerRepository.save(autoCustomer.getCustomer());
           autoCustomer.setCustomer(newCustomer);
        } else {
            autoCustomer.setCustomer(persistedCustomer.get());
        }
        autoCustomer.setAuto(persistedAuto.get());
        autoCustomerRepository.save(autoCustomer);
        return autoCustomer.getStatus();
    }

    @Override
    public String extendRent(Long id) throws CarSharingServiceException {
        return null;
    }

    @Override
    public List<AutoDto> findCars(String filter) throws CarSharingServiceException {
        return null;
    }

    private String closeRentProcess(Optional<AutoCustomer> autoCustomer) throws CarSharingServiceException {
        if (!autoCustomer.isPresent()) {
            LOGGER.error("Not found appropriate rent in DB!");
            throw new CarSharingServiceException("Не найдена соответствующая запись об аренде в БД!");
        }
        if (!autoCustomer.get().getStatus().contains(RIGHT_STATUS)) {
            LOGGER.error("The rent to close must be active!");
            throw new CarSharingServiceException("Для закрытия аренда должна быть в статусе открытой!");
        }
        RentStatus newStatus = checkService.isInTime(autoCustomer.get().getEndRent()) ? RentStatus.CLOSED : RentStatus.CLOSED_EXPIRED;
        autoCustomer.get().setStatus(newStatus.getStatus());
        autoCustomer.get().setClosedRent(LocalDateTime.now());
        return newStatus.getStatus();
    }
}
