package ch.bfh.bti7081.s2019.blue.server.mapper;

import org.dozer.DozerConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter extends DozerConverter<LocalDateTime, LocalDateTime> {

    public LocalDateTimeConverter() {
        super(LocalDateTime.class, LocalDateTime.class);
    }

    @Override
    public LocalDateTime convertTo(LocalDateTime source, LocalDateTime destination) {
        return LocalDateTime.of(source.toLocalDate(), source.toLocalTime());
    }

    @Override
    public LocalDateTime convertFrom(LocalDateTime source, LocalDateTime destination) {
        return LocalDateTime.of(source.toLocalDate(), source.toLocalTime());
    }
}
