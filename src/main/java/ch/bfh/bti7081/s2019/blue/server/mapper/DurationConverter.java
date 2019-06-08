package ch.bfh.bti7081.s2019.blue.server.mapper;

import org.dozer.DozerConverter;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationConverter extends DozerConverter<Duration, Duration> {

    public DurationConverter() {
        super(Duration.class, Duration.class);
    }

    @Override
    public Duration convertTo(Duration source, Duration destination) {
        if (source == null) {
            return null;
        }
        return Duration.from(source);
    }

    @Override
    public Duration convertFrom(Duration source, Duration destination) {
        if (source == null) {
            return null;
        }
        return Duration.from(source);
    }
}
