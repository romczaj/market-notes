package pl.romczaj.marketnotes.main.configration.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

import java.io.IOException;

public class UserAccountExternalIdJacksonDeserializer extends JsonDeserializer<UserAccountExternalId> {

    @Override
    public UserAccountExternalId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return UserAccountExternalId.fromString(p.getText());
    }
}
