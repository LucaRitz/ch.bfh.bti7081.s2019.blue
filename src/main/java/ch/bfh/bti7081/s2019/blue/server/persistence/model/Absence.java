package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "absence")
public class Absence {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return this.fromDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
