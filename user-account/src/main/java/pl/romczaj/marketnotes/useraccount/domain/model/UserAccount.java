package pl.romczaj.marketnotes.useraccount.domain.model;

import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.internalapi.AuthenticationRetriever.LoggedUser;

public record UserAccount(
        Long id,
        UserAccountExternalId externalId,
        String username,
        String email
) {


    public static UserAccount createFrom(LoggedUser loggedUser) {
        return new UserAccount(
                null,
                loggedUser.userAccountExternalId(),
                loggedUser.username(),
                loggedUser.email());
    }
}
