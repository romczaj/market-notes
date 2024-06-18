package pl.romczaj.marketnotes.common.mvc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;


@Component
public class UserAccountExternalIdMvcConverter implements Converter<String, UserAccountExternalId> {

    @Override
    public UserAccountExternalId convert(String source) {
        return UserAccountExternalId.fromString(source);
    }
}
