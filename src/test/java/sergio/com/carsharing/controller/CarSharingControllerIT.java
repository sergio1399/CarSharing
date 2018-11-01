package sergio.com.carsharing.controller;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import sergio.com.carsharing.CarSharingApplication;
import sergio.com.carsharing.dto.AutoDto;
import sergio.com.carsharing.dto.CustomerDto;
import sergio.com.carsharing.dto.RentDto;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarSharingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@Rollback
public class CarSharingControllerIT extends AbstractTransactionalJUnit4SpringContextTests {
    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String STATUS_ACTIVE = "{\"rentStatus\":\"active\"}";

    private static final String STATUS_CLOSED = "{\"rentStatus\":\"closed\"}";

    private static final String STATUS_CLOSED_EXPIRED = "{\"rentStatus\":\"closed expired\"}";

    private static final String ERROR_OCCUPIED_AUTO = "{\"message\":\"Данный момент автомобиль уже находится в аренде!\"}";

    private static final String ERROR_NOT_FOUND_AUTO = "{\"message\":\"Автомобиль не найден в БД!\"}";

    private static final String ERROR_NO_PARAMS = "{\"message\":\"Некорректные входные параметры\"}";

    @Test
    public void testOpenRent() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");
        String body = "{\n" +
                "\t\"auto\":{\n" +
                "\t\t\"brand\": \"Ford\",\n" +
                "\t\t\"model\": \"Eco Sport\",\n" +
                "\t\t\"vin\": \"34rte322eww\",\n" +
                "\t\t\"madeYear\": \"2015\"\n" +
                "\t},\n" +
                "\t\"customer\":{\n" +
                "\t\t\"name\": \"Quentin Tarantino\",\n" +
                "\t\t\"birthYear\": \"1960\",\n" +
                "\t\t\"passportNumber\": \"234343\"\n" +
                "\t},\n" +
                "\t\"startRent\": \"2018-10-31T15:59:59\",\n" +
                "\t\"endRent\": \"2018-11-02T18:59:59\",\n" +
                "\t\"status\": \"active\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/carSharing/openRent"),
                HttpMethod.POST, entity, String.class);

        String expected = STATUS_ACTIVE;

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testOpenRentOccupiedCar() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");
        String body = "{\n" +
                "\t\"auto\":{\n" +
                "\t\t\"brand\": \"Mazda\",\n" +
                "\t\t\"model\": \"6\",\n" +
                "\t\t\"vin\": \"43fdjwew432\",\n" +
                "\t\t\"madeYear\": \"2012\"\n" +
                "\t},\n" +
                "\t\"customer\":{\n" +
                "\t\t\"name\": \"Quentin Tarantino\",\n" +
                "\t\t\"birthYear\": \"1960\",\n" +
                "\t\t\"passportNumber\": \"234343\"\n" +
                "\t},\n" +
                "\t\"startRent\": \"2018-10-31T15:59:59\",\n" +
                "\t\"endRent\": \"2018-11-02T18:59:59\",\n" +
                "\t\"status\": \"active\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/carSharing/openRent"),
                HttpMethod.POST, entity, String.class);

        String expected = ERROR_OCCUPIED_AUTO;

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testOpenRentNotFoundCar() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");
        String body = "{\n" +
                "\t\"auto\":{\n" +
                "\t\t\"brand\": \"Toyota\",\n" +
                "\t\t\"model\": \"Corolla\",\n" +
                "\t\t\"vin\": \"43fdfdfdf32\",\n" +
                "\t\t\"madeYear\": \"2017\"\n" +
                "\t},\n" +
                "\t\"customer\":{\n" +
                "\t\t\"name\": \"Quentin Tarantino\",\n" +
                "\t\t\"birthYear\": \"1960\",\n" +
                "\t\t\"passportNumber\": \"234343\"\n" +
                "\t},\n" +
                "\t\"startRent\": \"2018-10-31T15:59:59\",\n" +
                "\t\"endRent\": \"2018-11-02T18:59:59\",\n" +
                "\t\"status\": \"active\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/carSharing/openRent"),
                HttpMethod.POST, entity, String.class);

        String expected = ERROR_NOT_FOUND_AUTO;

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCloseRentById() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/carSharing/closeRent?rentId=1"),
                HttpMethod.GET, entity, String.class);

        String expected = STATUS_CLOSED_EXPIRED;

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCloseRentByVinAndPassport() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/carSharing/closeRent?vin=43fdjwew432&passportNumber=234343"),
                HttpMethod.GET, entity, String.class);

        String expected = STATUS_CLOSED_EXPIRED;

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCloseRentNoParams() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/carSharing/closeRent"),
                HttpMethod.GET, entity, String.class);

        String expected = ERROR_NO_PARAMS;

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}