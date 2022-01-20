package com.dev.anbu.utils;

import com.dev.anbu.utils.LocalDateDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocalDateDeserializerTest {

    @Mock
    private JsonParser parser;

    @Mock
    private DeserializationContext context;

    private LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer();

    @Test
    public void deserializeCorrectTest() throws IOException {
        when(parser.getText()).thenReturn("1989-01-09");
        LocalDate dateExpect = LocalDate.of(1989, 1, 9);
        LocalDate dateTest = localDateDeserializer.deserialize(parser, context);
        assertEquals(dateExpect, dateTest);
    }

    @Test
    public void deserializeFailTest() throws IOException {
        when(parser.getText()).thenReturn("1989/01/09");
        LocalDate dateExpect = LocalDate.of(1989, 1, 9);
        LocalDate dateTest = localDateDeserializer.deserialize(parser, context);
        assertNotEquals(dateExpect, dateTest);
    }

}
