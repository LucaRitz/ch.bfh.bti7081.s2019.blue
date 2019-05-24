package ch.bfh.bti7081.s2019.blue.client.rest;

public class Promises {

    public static <V> RestPromise<V> fulfill(final V value) {
        RestPromise<V> promise = new RestPromise<>();
        promise.fulfill(value);
        return promise;
    }

    public static <V> RestPromise<V> reject(final Exception reason) {
        RestPromise<V> promise = new RestPromise<>();
        promise.reject(reason);
        return promise;
    }
}
