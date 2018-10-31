package sergio.com.carsharing.dto.enums;

public enum RentStatus {
    ACTIVE("active"),
    ACTIVE_EXPIRED("active expired"),
    CLOSED("closed"),
    CLOSED_EXPIRED("closed expired");

    private String status;

    RentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
