package sergio.com.carsharing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import sergio.com.carsharing.model.Customer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RentDto {

    private Long id;

    @NotNull
    @Valid
    @JsonProperty("auto")
    private AutoDto autoDto;

    @NotNull
    @Valid
    @JsonProperty("customer")
    private CustomerDto customerDto;

    @NotNull
    private LocalDateTime startRent;

    @NotNull
    private LocalDateTime endRent;

    private LocalDateTime closedRent;

    @NotEmpty
    private String status;

    public RentDto() {
    }

    public AutoDto getAutoDto() {
        return autoDto;
    }

    public void setAutoDto(AutoDto autoDto) {
        this.autoDto = autoDto;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public LocalDateTime getStartRent() {
        return startRent;
    }

    public void setStartRent(LocalDateTime startRent) {
        this.startRent = startRent;
    }

    public LocalDateTime getEndRent() {
        return endRent;
    }

    public void setEndRent(LocalDateTime endRent) {
        this.endRent = endRent;
    }

    public LocalDateTime getClosedRent() {
        return closedRent;
    }

    public void setClosedRent(LocalDateTime closedRent) {
        this.closedRent = closedRent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RentDto{" +
                "autoDto=" + autoDto +
                ", customerDto=" + customerDto +
                ", startRent=" + startRent +
                ", endRent=" + endRent +
                ", closedRent=" + closedRent +
                '}';
    }
}
