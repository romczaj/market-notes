package pl.romczaj.marketnotes.main.configration.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.io.IOException;

public class StockCompanyExternalIdJacksonDeserializer extends JsonDeserializer<StockCompanyExternalId> {

    @Override
    public StockCompanyExternalId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return StockCompanyExternalId.fromString(p.getText());
    }
}
