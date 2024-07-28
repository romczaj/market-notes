package pl.romczaj.marketnotes.useraccount.domain.model;

import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.AddAccountRequest;

public record UserAccount(
        Long id,
        UserAccountExternalId externalId,
        String username,
        String email
) implements DomainModel {


    public static UserAccount createFrom(AddAccountRequest addAccountRequest) {
        return new UserAccount(
                null,
                addAccountRequest.userAccountExternalId(),
                addAccountRequest.username(),
                addAccountRequest.email());
    }
}
