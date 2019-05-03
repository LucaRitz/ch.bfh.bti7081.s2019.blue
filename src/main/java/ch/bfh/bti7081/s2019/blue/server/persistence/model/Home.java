package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "home")
public class Home extends BaseEntity {

    @Column(name = "text")
    private String text;

    @Column(name = "reference", nullable = false)
    private Long reference;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }
}
