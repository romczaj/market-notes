package pl.romczaj.marketnotes.common.persistance;



import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

import java.util.UUID;

@Converter(autoApply = true)
public class UserAccountExternalIdDatabaseConverter implements AttributeConverter<UserAccountExternalId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(UserAccountExternalId attribute) {
        return attribute.accountId();
    }

    @Override
    public UserAccountExternalId convertToEntityAttribute(UUID dbData) {
        return new UserAccountExternalId(dbData);
    }
}
