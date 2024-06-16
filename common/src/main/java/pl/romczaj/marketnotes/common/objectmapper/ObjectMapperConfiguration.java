package pl.romczaj.marketnotes.common.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalIdJacksonDeserializer;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalIdJacksonSerializer;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleModule stockCompanyExternalModule = new SimpleModule();
        stockCompanyExternalModule.addDeserializer(StockCompanyExternalId.class, new StockCompanyExternalIdJacksonDeserializer());
        stockCompanyExternalModule.addSerializer(StockCompanyExternalId.class, new StockCompanyExternalIdJacksonSerializer());

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        mapper.registerModule(stockCompanyExternalModule);

        return mapper;
    }
}