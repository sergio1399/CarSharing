package sergio.com.carsharing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sergio.com.carsharing.dto.RentDto;
import sergio.com.carsharing.dto.ResponseDto;
import sergio.com.carsharing.exception.CarSharingServiceException;
import sergio.com.carsharing.model.Customer;
import sergio.com.carsharing.service.CarSharingService;

@RestController
@RequestMapping(value = "/carSharing")
public class CarSharingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSharingController.class);

    private CarSharingService service;

    public CarSharingController(CarSharingService service) {
        this.service = service;
    }

    @PostMapping(value = "/openRent", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseDto> openRent(@Validated @RequestBody RentDto rentDto) throws CarSharingServiceException {
        String status = service.openRent(rentDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setRentStatus(status);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(value = "/closeRent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseDto> closeRentById(@RequestParam(required = false) Long rentId,
                                              @RequestParam(required = false) String vin,
                                              @RequestParam(required = false) String passportNumber) throws CarSharingServiceException {
        String status = null;
        if (rentId != null) {
            status = service.closeRent(rentId);
        } else if (!StringUtils.isEmpty(vin) && !StringUtils.isEmpty(passportNumber)){
            status = service.closeRent(vin, passportNumber);
        } else {
            throw new CarSharingServiceException("Некорректные входные параметры");
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setRentStatus(status);
        return ResponseEntity.ok().body(responseDto);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> validationErrorHandler(MethodArgumentNotValidException e) {
        LOGGER.error("Method parameters aren't valid: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ResponseDto("Некорректные входные параметры"));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> carSharingErrorHandler(CarSharingServiceException e) {
        LOGGER.error("Car Sharing service generated exception with message: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> runtimeErrorHandler(RuntimeException e) {
        LOGGER.error("Unexpected exception with message: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( new ResponseDto("Непредвиденная ошибка сервиса"));
    }
}
