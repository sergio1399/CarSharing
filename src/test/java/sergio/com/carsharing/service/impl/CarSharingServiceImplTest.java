package sergio.com.carsharing.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sergio.com.carsharing.dao.AutoCustomerRepository;
import sergio.com.carsharing.dao.AutoRepository;
import sergio.com.carsharing.dao.CustomerRepository;
import sergio.com.carsharing.dto.AutoDto;
import sergio.com.carsharing.dto.CustomerDto;
import sergio.com.carsharing.dto.RentDto;
import sergio.com.carsharing.exception.CarSharingServiceException;
import sergio.com.carsharing.model.Auto;
import sergio.com.carsharing.model.AutoCustomer;
import sergio.com.carsharing.model.Customer;
import sergio.com.carsharing.service.CheckService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {CarSharingServiceImpl.class})
@EnableAutoConfiguration
public class CarSharingServiceImplTest {
    @Autowired
    CarSharingServiceImpl service;

    @MockBean
    CheckService checkService;

    @MockBean
    AutoCustomerRepository autoCustomerRepository;

    @MockBean
    AutoRepository autoRepository;

    @MockBean
    CustomerRepository customerRepository;

    private static final String BRAND = "Mazda";

    private static final String MODEL = "6";

    private static final String VIN = "2323zz";

    private static final Integer MADE_YEAR = 2012;

    private static final String NAME = "Quentin Tarantino";

    private static final String PASSPORT_NUMBER = "993317";

    private static final Integer BIRTH_YEAR = 1960;

    private static final String STATUS = "active";

    private static final String STATUS_CLOSED = "closed";

    private static final String STATUS_CLOSED_EXPIRED = "closed expired";

    private static final LocalDateTime START_RENT = LocalDateTime.parse("2018-10-30T10:59:59");

    private static final LocalDateTime END_RENT = LocalDateTime.parse("2018-10-31T23:59:59");

    private static final Long RENT_ID = 1L;

    @Test
    public void openRentWithNewCustomerTest() throws CarSharingServiceException {
        when(autoRepository.findByVin(VIN)).thenReturn(Optional.of(getAuto()));
        when(autoCustomerRepository.findByAuto_VinAndStatus(VIN, STATUS)).thenReturn(Optional.empty());
        when(customerRepository.findByPassportNumber(PASSPORT_NUMBER)).thenReturn(Optional.empty());
        when(customerRepository.save(getCustomer())).thenReturn(getCustomerPersisted());
        when(autoCustomerRepository.save(getAutoCustomer())).thenReturn(getAutoCustomerPersisted());

        String rentStatus = service.openRent(getRentDtoWithNewCustomer());

        Assert.assertEquals(STATUS, rentStatus);
    }

    @Test
    public void openRentWithExistsCustomerTest() throws CarSharingServiceException {
        when(autoRepository.findByVin(VIN)).thenReturn(Optional.of(getAuto()));
        when(autoCustomerRepository.findByAuto_VinAndStatus(VIN, STATUS)).thenReturn(Optional.empty());
        when(customerRepository.findByPassportNumber(PASSPORT_NUMBER)).thenReturn(Optional.of(getCustomerPersisted()));
        when(autoCustomerRepository.save(getAutoCustomer())).thenReturn(getAutoCustomerPersisted());

        String rentStatus = service.openRent(getRentDtoWithNewCustomer());

        Assert.assertEquals(STATUS, rentStatus);
    }

    @Test(expected = CarSharingServiceException.class)
    public void openRentWithWrongAuto() throws CarSharingServiceException {
        when(autoRepository.findByVin(VIN)).thenReturn(Optional.empty());

        String rentStatus = service.openRent(getRentDtoWithNewCustomer());
    }

    @Test(expected = CarSharingServiceException.class)
    public void openRentWithOccupiedAuto() throws CarSharingServiceException {
        when(autoRepository.findByVin(VIN)).thenReturn(Optional.of(getAuto()));
        when(autoCustomerRepository.findByAuto_VinAndStatus(VIN, STATUS)).thenReturn(Optional.of(getAutoCustomer()));

        String rentStatus = service.openRent(getRentDtoWithNewCustomer());
    }

    @Test
    public void closeRentByIdInTime() throws CarSharingServiceException {
        when(autoCustomerRepository.findById(RENT_ID)).thenReturn(Optional.of(getAutoCustomerPersisted()));
        when(checkService.isInTime(getAutoCustomerPersisted().getEndRent())).thenReturn(true);
        String rentStatus = service.closeRent(RENT_ID);

        Assert.assertEquals(STATUS_CLOSED, rentStatus);
    }

    @Test
    public void closeRentByIdOutOfTime() throws CarSharingServiceException {
        when(autoCustomerRepository.findById(RENT_ID)).thenReturn(Optional.of(getAutoCustomerPersisted()));
        when(checkService.isInTime(getAutoCustomerPersisted().getEndRent())).thenReturn(false);
        String rentStatus = service.closeRent(RENT_ID);

        Assert.assertEquals(STATUS_CLOSED_EXPIRED, rentStatus);
    }

    @Test
    public void closeRentByPassportAndVinInTime() throws CarSharingServiceException {
        when(autoCustomerRepository.findByAuto_VinAndCustomer_PassportNumberAndStatus(VIN, PASSPORT_NUMBER, STATUS)).
                thenReturn(Optional.of(getAutoCustomerPersisted()));
        when(checkService.isInTime(getAutoCustomerPersisted().getEndRent())).thenReturn(true);
        String rentStatus = service.closeRent(VIN, PASSPORT_NUMBER);

        Assert.assertEquals(STATUS_CLOSED, rentStatus);
    }

    @Test
    public void closeRentByPassportAndVinOutOfTime() throws CarSharingServiceException {
        when(autoCustomerRepository.findByAuto_VinAndCustomer_PassportNumberAndStatus(VIN, PASSPORT_NUMBER, STATUS)).
                thenReturn(Optional.of(getAutoCustomerPersisted()));
        when(checkService.isInTime(getAutoCustomerPersisted().getEndRent())).thenReturn(false);
        String rentStatus = service.closeRent(VIN, PASSPORT_NUMBER);

        Assert.assertEquals(STATUS_CLOSED_EXPIRED, rentStatus);
    }

    @Test(expected = CarSharingServiceException.class)
    public void closeRentByIdNotFound() throws CarSharingServiceException {
        when(autoCustomerRepository.findById(RENT_ID)).thenReturn(Optional.empty());

        String rentStatus = service.closeRent(RENT_ID);
    }

    @Test(expected = CarSharingServiceException.class)
    public void closeRentByIdAlreadyClosed() throws CarSharingServiceException {
        when(autoCustomerRepository.findById(RENT_ID)).thenReturn(Optional.of(getAutoCustomerClosed()));

        String rentStatus = service.closeRent(RENT_ID);
    }

    @Test(expected = CarSharingServiceException.class)
    public void closeRentByPassportAndVinNotFound() throws CarSharingServiceException {
        when(autoCustomerRepository.findByAuto_VinAndCustomer_PassportNumberAndStatus(VIN, PASSPORT_NUMBER, STATUS)).
                thenReturn(Optional.empty());

        String rentStatus = service.closeRent(VIN, PASSPORT_NUMBER);
    }

    @Test(expected = CarSharingServiceException.class)
    public void closeRentByPasspoertAndVinAlreadyClosed() throws CarSharingServiceException {
        when(autoCustomerRepository.findByAuto_VinAndCustomer_PassportNumberAndStatus(VIN, PASSPORT_NUMBER, STATUS)).
                thenReturn(Optional.of(getAutoCustomerClosed()));

        String rentStatus = service.closeRent(VIN, PASSPORT_NUMBER);
    }

    private AutoCustomer getAutoCustomerClosed() {
        AutoCustomer autoCustomer = getAutoCustomer();
        autoCustomer.setStatus(STATUS_CLOSED);
        return autoCustomer;
    }

    private Auto getAuto() {
        Auto auto = new Auto();
        auto.setBrand(BRAND);
        auto.setModel(MODEL);
        auto.setMadeYear(MADE_YEAR);
        auto.setVin(VIN);
        return auto;
    }

    private Auto getAutoPersisted() {
        Auto auto = getAuto();
        auto.setId(1L);
        return auto;
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setName(NAME);
        customer.setPassportNumber(PASSPORT_NUMBER);
        customer.setBirthYear(BIRTH_YEAR);
        return customer;
    }

    private Customer getCustomerPersisted() {
        Customer customer = getCustomer();
        customer.setId(1L);
        return customer;
    }

    private AutoCustomer getAutoCustomer() {
        AutoCustomer autoCustomer = new AutoCustomer();
        autoCustomer.setCustomer(getCustomer());
        autoCustomer.setAuto(getAuto());
        autoCustomer.setEndRent(END_RENT);
        autoCustomer.setStartRent(START_RENT);
        autoCustomer.setStatus(STATUS);
        return autoCustomer;
    }

    private AutoCustomer getAutoCustomerPersisted() {
        AutoCustomer autoCustomer = getAutoCustomer();
        autoCustomer.setId(1L);
        return autoCustomer;
    }

    private AutoDto getAutoDto() {
        AutoDto autoDto = new AutoDto();
        autoDto.setBrand(BRAND);
        autoDto.setModel(MODEL);
        autoDto.setMadeYear(MADE_YEAR);
        autoDto.setVin(VIN);
        return autoDto;
    }

    private CustomerDto getCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(NAME);
        customerDto.setPassportNumber(PASSPORT_NUMBER);
        customerDto.setBirthYear(BIRTH_YEAR);
        return customerDto;
    }

    private RentDto getRentDtoWithNewCustomer() {
        RentDto rentDto = new RentDto();
        rentDto.setAutoDto(getAutoDto());
        rentDto.setCustomerDto(getCustomerDto());
        rentDto.setStatus(STATUS);
        rentDto.setStartRent(START_RENT);
        rentDto.setEndRent(END_RENT);
        return rentDto;
    }

}