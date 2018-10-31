package sergio.com.carsharing.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(name = "birth_year", nullable = false)
    private Integer birthYear;

    @Column(nullable = false, length = 10, unique = true)
    private String passportNumber;

    @OneToMany(mappedBy = "customer")
    private List<AutoCustomer> autoCustomers;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<AutoCustomer> getAutoCustomers() {
        return autoCustomers;
    }

    public void setAutoCustomers(List<AutoCustomer> autoCustomers) {
        this.autoCustomers = autoCustomers;
    }


}
