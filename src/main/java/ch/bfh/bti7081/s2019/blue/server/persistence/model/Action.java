package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "action")
public class Action {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "done")
    private boolean done;

    @ManyToOne
    @JoinColumn
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
