package ch.bfh.bti7081.s2019.blue.client.rest;

import javax.annotation.Nullable;
import java.util.Objects;

public class RestPromise<V> implements Promise<V> {

    private V value;
    private State state = State.PENDING;;
    private Exception reason;

    private Handler<V> first;
    private Handler<V> last;

    public synchronized void fulfill(@Nullable V value) {
        if (state != State.PENDING) {
            throw new IllegalStateException("Promise already fulfilled");
        }

        state = State.FULFILLED;
        this.value = value;

        for (Handler<V> handler = first; handler != null; handler = handler.next) {
            handler.fulfill(value);
        }
    }

    public synchronized void reject(Exception reason) {
        if (state != State.PENDING) {
            throw new IllegalStateException("Promise already rejected");
        }

        state = State.REJECTED;
        this.reason = reason;

        for (Handler<V> handler = first; handler != null; handler = handler.next) {
            handler.reject(reason);
        }
        first = null;
        last = null;
    }

    @Override
    public <R> Promise<R> then(ThenCallback<? super V, R> callback) {
        switch (state) {
            case FULFILLED:
                return callback.onFulfilled(value);
            case REJECTED:
                return callback.onRejected(reason);
            case PENDING:
            default:
                Objects.requireNonNull(callback);
                final RestPromise<R> promise = new RestPromise<>();
                addHandler(new Handler<V>() {
                    @Override
                    void fulfill(V value) {
                        chain(callback.onFulfilled(value), promise);
                    }

                    @Override
                    void reject(Exception reason) {
                        chain(callback.onRejected(reason), promise);
                    }

                    private void chain(Promise<R> promise, final RestPromise<R> into) {
                        promise.done(new DoneCallback<R>() {
                            @Override
                            public void onFulfilled(R value) {
                                into.fulfill(value);
                            }

                            @Override
                            public void onRejected(Exception reason) {
                                into.reject(reason);
                            }
                        });
                    }
                });
                return promise;
        }
    }

    @Override
    public void done(DoneCallback<? super V> callback) {
        switch (state) {
            case FULFILLED:
                callback.onFulfilled(value);
                break;
            case REJECTED:
                callback.onRejected(reason);
                break;
            case PENDING:
            default:
                Objects.requireNonNull(callback);
                addHandler(new Handler<V>() {
                    @Override
                    void fulfill(V value) {
                        callback.onFulfilled(value);
                    }

                    @Override
                    void reject(Exception reason) {
                        callback.onRejected(reason);
                    }
                });
        }
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public State getState() {
        return state;
    }

    private void addHandler(Handler<V> handler) {
        if (last == null) {
            first = handler;
        } else {
            last.next = handler;
        }
        last = handler;
    }

    private abstract static class Handler<V> {
        private Handler<V> next;

        abstract void fulfill(V value);

        abstract void reject(Exception reason);
    }
}
