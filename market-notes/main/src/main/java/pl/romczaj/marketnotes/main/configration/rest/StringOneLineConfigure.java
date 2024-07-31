package pl.romczaj.marketnotes.main.configration.rest;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import pl.romczaj.marketnotes.common.id.StringOneLine;

import java.io.IOException;
import java.util.function.Function;

@AllArgsConstructor
public class StringOneLineConfigure<T extends StringOneLine> {

    private Class<T> valueObject;
    private Function<String, T> createFunction;
    private Function<T, String> toStringFunction;

    public Class<T> getType() {
        return valueObject;
    }

    public JsonDeserializer<T> toJsonDeserializer() {
        return new JsonDeserializer<>() {
            @Override
            public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                return createFunction.apply(deserializationContext.toString());
            }
        };
    }

    public JsonSerializer<T> toJsonSerializer() {
        return new JsonSerializer<T>() {
            @Override
            public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(toStringFunction.apply(t));
            }
        };
    }

    public Converter<String, T> toMvcConvert() {
        return new Converter<String, T>() {
            @Override
            public T convert(String source) {
                return createFunction.apply(source);
            }
        };
    }

}
