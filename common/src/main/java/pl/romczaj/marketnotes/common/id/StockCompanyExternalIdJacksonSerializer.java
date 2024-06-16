package pl.romczaj.marketnotes.common.id;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StockCompanyExternalIdJacksonSerializer extends JsonSerializer<StockCompanyExternalId> {

    @Override
    public void serialize(StockCompanyExternalId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
