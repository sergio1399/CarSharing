package sergio.com.carsharing.dto;

import sergio.com.carsharing.model.Customer;

import java.time.LocalDateTime;

public class RentDto {

    private AutoDto autoDto;

    private CustomerDto customerDto;

    private LocalDateTime startRent;

    private LocalDateTime endRent;

    private LocalDateTime closedRent;
}
