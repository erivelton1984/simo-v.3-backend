package br.com.ciccr.simo.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ProtocolGenerator {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    public String generate(String atd, Long id) {

        return "%s-%06d".formatted(
                LocalDate.now().format(FORMATTER),
                id
        );
    }

}