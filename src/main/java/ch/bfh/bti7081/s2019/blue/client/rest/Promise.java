package ch.bfh.bti7081.s2019.blue.client.rest;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Promise<V> {
    enum State {
        PENDING,
        FULFILLED,
        REJECTED,
    }

    abstract class ThenCallback<V, R> {
        public abstract Promise<R> onFulfilled(@Nullable V value);

        public Promise<R> onRejected(Exception reason) {
            return Promises.reject(reason);
        }
    }

    abstract class DoneCallback<V> {

        public abstract void onFulfilled(@Nullable V value);

        public void onRejected(Exception reason) {
            // NOOP
        }
    }

    <R> Promise<R> then(ThenCallback<? super V, R> callback);

    void done(DoneCallback<? super V> callback);

    V getValue();

    State getState();

    default <R> Promise<R> then(Function<? super V, Promise<R>> function) {
        return then(new ThenCallback<V, R>() {
            @Override
            public Promise<R> onFulfilled(@Nullable V value) {
                return function.apply(value);
            }
        });
    }

    default void done(Consumer<? super V> consumer) {
        done(new DoneCallback<V>() {
            @Override
            public void onFulfilled(@Nullable V value) {
                consumer.accept(value);
            }
        });
    }
}
