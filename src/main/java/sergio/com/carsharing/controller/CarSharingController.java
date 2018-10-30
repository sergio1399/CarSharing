package sergio.com.carsharing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping(value = "/openRent")
    ResponseEntity<Customer> openRent() {

        return null;
    }

    @PostMapping(value = "/closeRent")
    ResponseEntity<Customer> closeRent() {

        return null;
    }
}
