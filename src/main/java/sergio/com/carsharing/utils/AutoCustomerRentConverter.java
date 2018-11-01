package sergio.com.carsharing.utils;

import org.modelmapper.ModelMapper;
import sergio.com.carsharing.dto.AutoDto;
import sergio.com.carsharing.dto.CustomerDto;
import sergio.com.carsharing.dto.RentDto;
import sergio.com.carsharing.model.Auto;
import sergio.com.carsharing.model.AutoCustomer;
import sergio.com.carsharing.model.Customer;

import java.text.ParseException;

public class AutoCustomerRentConverter {
    public static RentDto convertToDto(AutoCustomer autoCustomer, ModelMapper modelMapper) {
        //RentDto rentDto = modelMapper.map(autoCustomer, RentDto.class);
        //rentDto.setAutoDto(modelMapper.map(autoCustomer.getAuto(), AutoDto.class));
        //rentDto.setCustomerDto(modelMapper.map(autoCustomer.getCustomer(), CustomerDto.class));
        RentDto rentDto = new RentDto();
        rentDto.setClosedRent(autoCustomer.getClosedRent());
        rentDto.setStartRent(autoCustomer.getStartRent());
        rentDto.setEndRent(autoCustomer.getEndRent());
        rentDto.setStatus(autoCustomer.getStatus());
        AutoDto autoDto = new AutoDto();
        Auto auto = autoCustomer.getAuto();
        autoDto.setBrand(auto.getBrand());
        autoDto.setModel(auto.getModel());
        autoDto.setVin(auto.getVin());
        autoDto.setMadeYear(auto.getMadeYear());
        rentDto.setAutoDto(autoDto);
        CustomerDto customerDto = new CustomerDto();
        Customer customer = autoCustomer.getCustomer();
        customerDto.setName(customer.getName());
        customerDto.setPassportNumber(customer.getPassportNumber());
        customerDto.setBirthYear(customer.getBirthYear());
        rentDto.setCustomerDto(customerDto);
        return rentDto;
    }

    public static AutoCustomer convertToModel(RentDto rentDto, ModelMapper modelMapper) {
        /*AutoCustomer autoCustomer = modelMapper.map(rentDto, AutoCustomer.class);
        autoCustomer.setAuto(modelMapper.map(rentDto.getAutoDto(), Auto.class));
        autoCustomer.setCustomer(modelMapper.map(rentDto.getCustomerDto(), Customer.class));*/
        AutoCustomer autoCustomer = new AutoCustomer();
        autoCustomer.setStatus(rentDto.getStatus());
        autoCustomer.setClosedRent(rentDto.getClosedRent());
        autoCustomer.setStartRent(rentDto.getStartRent());
        autoCustomer.setEndRent(rentDto.getEndRent());
        Auto auto = new Auto();
        AutoDto autoDto = rentDto.getAutoDto();
        auto.setVin(autoDto.getVin());
        auto.setBrand(autoDto.getBrand());
        auto.setModel(autoDto.getModel());
        auto.setMadeYear(autoDto.getMadeYear());
        autoCustomer.setAuto(auto);
        Customer customer = new Customer();
        CustomerDto customerDto = rentDto.getCustomerDto();
        customer.setName(customerDto.getName());
        customer.setPassportNumber(customerDto.getPassportNumber());
        customer.setBirthYear(customerDto.getBirthYear());
        autoCustomer.setCustomer(customer);
        return autoCustomer;
    }
}
