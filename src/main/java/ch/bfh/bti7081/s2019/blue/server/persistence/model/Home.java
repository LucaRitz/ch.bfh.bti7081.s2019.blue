package ch.bfh.bti7081.s2019.blue.server.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_HOME")
public class Home extends BaseEntity {

    @Column(name = "COL_TEXT")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
