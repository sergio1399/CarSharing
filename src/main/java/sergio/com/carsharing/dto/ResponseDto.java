package sergio.com.carsharing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rentStatus;

    public ResponseDto(String message) {
        this.message = message;
    }

    public ResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "message='" + message + '\'' +
                ", rentStatus='" + rentStatus + '\'' +
                '}';
    }
}
