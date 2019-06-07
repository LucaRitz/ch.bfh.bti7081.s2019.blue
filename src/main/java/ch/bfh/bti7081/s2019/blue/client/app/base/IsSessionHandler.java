package ch.bfh.bti7081.s2019.blue.client.app.base;

public interface IsSessionHandler {

    <T> void set(String key, T parameter);

    <T> T get(String key);
}
