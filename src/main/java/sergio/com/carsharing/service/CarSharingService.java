package sergio.com.carsharing.service;

import sergio.com.carsharing.dto.AutoDto;
import sergio.com.carsharing.dto.RentDto;
import sergio.com.carsharing.exception.CarSharingServiceException;

import java.util.List;

public interface CarSharingService {

    String closeRent(String vin, String passportNumber) throws CarSharingServiceException;

    String closeRent(Long id) throws CarSharingServiceException;

    String openRent(RentDto rentDto) throws CarSharingServiceException;

    String extendRent(Long id) throws CarSharingServiceException;

    List<AutoDto> findCars(String filter) throws CarSharingServiceException;
}
