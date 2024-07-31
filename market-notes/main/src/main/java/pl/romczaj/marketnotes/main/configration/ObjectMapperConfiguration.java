package pl.romczaj.marketnotes.main.configration;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.romczaj.marketnotes.common.id.StringOneLine;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static pl.romczaj.marketnotes.main.configration.rest.StringOneLineConfigureList.OBJECT_REGISTERS;

@Configuration
public class ObjectMapperConfiguration {

    private static SimpleModule marketNotesModule() {
        SimpleModule marketNotesModule = new SimpleModule();
        OBJECT_REGISTERS.forEach(o -> {
            Class<StringOneLine> type = (Class<StringOneLine>) o.getType();
            JsonSerializer<StringOneLine> jsonSerializer = (JsonSerializer<StringOneLine>) o.toJsonSerializer();
            marketNotesModule.addDeserializer(type, o.toJsonDeserializer());
            marketNotesModule.addSerializer(type, jsonSerializer);
        });
        return marketNotesModule;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        mapper.registerModule(marketNotesModule());

        return mapper;
    }
}