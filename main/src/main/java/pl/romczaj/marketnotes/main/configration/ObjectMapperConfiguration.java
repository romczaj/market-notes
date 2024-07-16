package pl.romczaj.marketnotes.main.configration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.romczaj.marketnotes.common.id.*;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleModule marketNotesModule = new SimpleModule();
        marketNotesModule.addDeserializer(StockCompanyExternalId.class, new StockCompanyExternalIdJacksonDeserializer());
        marketNotesModule.addSerializer(StockCompanyExternalId.class, new StockCompanyExternalIdJacksonSerializer());

        marketNotesModule.addDeserializer(UserAccountExternalId.class, new UserAccountExternalIdJacksonDeserializer());
        marketNotesModule.addSerializer(UserAccountExternalId.class, new UserAccountExternalIdJacksonSerializer());

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        mapper.registerModule(marketNotesModule);

        return mapper;
    }
}