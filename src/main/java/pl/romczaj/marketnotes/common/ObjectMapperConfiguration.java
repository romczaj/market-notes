package pl.romczaj.marketnotes.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);

        return mapper;
    }
}