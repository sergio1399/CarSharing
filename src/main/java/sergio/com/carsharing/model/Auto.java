package sergio.com.carsharing.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "auto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String brand;

    @Column(nullable = false, length = 32)
    private String model;

    @Column(nullable = false, length = 17, unique = true)
    private String vin;

    @Column(name = "made_year", nullable = false)
    private Integer madeYear;

    @OneToMany(mappedBy = "auto")
    private List<AutoCustomer> autoCustomers;

    public Auto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<AutoCustomer> getAutoCustomers() {
        return autoCustomers;
    }

    public void setAutoCustomers(List<AutoCustomer> autoCustomers) {
        this.autoCustomers = autoCustomers;
    }

}
