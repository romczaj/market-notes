package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.respose;

import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

public record AddAccountResponse(
        String username,
        UserAccountExternalId userAccountExternalId
) {
}
