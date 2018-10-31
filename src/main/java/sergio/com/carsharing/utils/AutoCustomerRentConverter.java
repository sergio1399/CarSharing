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
        RentDto rentDto = modelMapper.map(autoCustomer, RentDto.class);
        rentDto.setAutoDto(modelMapper.map(autoCustomer.getAuto(), AutoDto.class));
        rentDto.setCustomerDto(modelMapper.map(autoCustomer.getCustomer(), CustomerDto.class));
        /*rentDto.setClosedRent(autoCustomer.getClosedRent());
        rentDto.setStartRent(autoCustomer.getStartRent());
        rentDto.setEndRent(autoCustomer.getEndRent());
        rentDto.setStatus(autoCustomer.getStatus());*/

        return rentDto;
    }

    public static AutoCustomer convertToModel(RentDto rentDto, ModelMapper modelMapper) {
        AutoCustomer autoCustomer = modelMapper.map(rentDto, AutoCustomer.class);
        autoCustomer.setAuto(modelMapper.map(rentDto.getAutoDto(), Auto.class));
        autoCustomer.setCustomer(modelMapper.map(rentDto.getCustomerDto(), Customer.class));
        return autoCustomer;
    }
}
