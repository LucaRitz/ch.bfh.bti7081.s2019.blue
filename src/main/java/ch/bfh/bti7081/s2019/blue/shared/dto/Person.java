package ch.bfh.bti7081.s2019.blue.shared.dto;

public interface Person {

    String getFirstname();

    String getLastname();

    default String getDisplayName() {
        return getFirstname() + " " + getLastname();
    }
}
