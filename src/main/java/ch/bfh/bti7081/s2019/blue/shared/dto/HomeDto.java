package ch.bfh.bti7081.s2019.blue.shared.dto;

import javax.validation.constraints.Size;

public class HomeDto {

    @Size(max = 20)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
