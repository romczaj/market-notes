package pl.romczaj.marketnotes.main.configration.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

import java.io.IOException;

public class UserAccountExternalIdJacksonSerializer extends JsonSerializer<UserAccountExternalId> {

    @Override
    public void serialize(UserAccountExternalId userAccountExternalId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(userAccountExternalId.accountId().toString());
    }
}
