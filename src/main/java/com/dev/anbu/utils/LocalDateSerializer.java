package com.dev.anbu.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final Logger log = LoggerFactory.getLogger(LocalDateSerializer.class);

    @Override
    public void serialize(
            LocalDate value,
            JsonGenerator gen,
            SerializerProvider serializers) throws IOException {

        try {
            String dateString = value.format(DateTimeFormatter.ISO_LOCAL_DATE);
            gen.writeString(dateString);
        } catch (DateTimeParseException e) {
            log.error(e.getMessage());
        }
    }

}
