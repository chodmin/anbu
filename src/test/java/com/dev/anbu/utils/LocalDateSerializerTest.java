package com.dev.anbu.utils;

import com.dev.anbu.utils.LocalDateSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocalDateSerializerTest {

    @Mock
    private JsonGenerator generator;

    @Mock
    private SerializerProvider provider;

    private LocalDateSerializer localDateSerializer = new LocalDateSerializer();

    @Test
    public void serializeCorrectTest() throws IOException {
        LocalDate value = LocalDate.of(1989, 1, 9);
        localDateSerializer.serialize(value, generator, provider);
    }

    @Test
    public void serializeFailTest() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> localDateSerializer.serialize(null, generator, provider));
    }

}
