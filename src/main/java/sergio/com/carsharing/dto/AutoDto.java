package sergio.com.carsharing.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AutoDto {

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotEmpty
    private String vin;

    @NotNull
    private Integer madeYear;

    public AutoDto() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getMadeYear() {
        return madeYear;
    }

    public void setMadeYear(Integer madeYear) {
        this.madeYear = madeYear;
    }

    @Override
    public String toString() {
        return "AutoDto{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", vin='" + vin + '\'' +
                ", madeYear=" + madeYear +
                '}';
    }
}
