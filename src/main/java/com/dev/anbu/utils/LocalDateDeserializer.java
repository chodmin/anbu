package com.dev.anbu.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final Logger log = LoggerFactory.getLogger(LocalDateDeserializer.class);

    @Override
    public LocalDate deserialize(
            JsonParser p,
            DeserializationContext ctxt) throws IOException, JsonProcessingException {

        LocalDate dateDeserialize = null;
        String dateString = p.getText();
        try {
            dateDeserialize = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            log.error("El formato de la fecha: {} no coincide con el formato YYYY-MM-DD", dateString);
        }
        return dateDeserialize;
    }

}
