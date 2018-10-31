package sergio.com.carsharing.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerDto {

    @NotEmpty
    private String name;

    @NotNull
    private Integer birthYear;

    @NotEmpty
    private String passportNumber;

    public CustomerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", passportNumber='" + passportNumber + '\'' +
                '}';
    }
}
