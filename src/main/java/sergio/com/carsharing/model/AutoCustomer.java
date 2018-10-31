package sergio.com.carsharing.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auto_customer")
public class AutoCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_rent", nullable = false)
    private LocalDateTime startRent;

    @Column(name = "end_rent", nullable = false)
    private LocalDateTime endRent;

    @Column(name = "closed_rent")
    private LocalDateTime closedRent;

    @Column(nullable = false)
    private String status;

    //@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_id")
    private Auto auto;

    //@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public AutoCustomer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
