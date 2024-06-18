package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

public interface UserAccountOperationRequest {
    UserAccountExternalId userAccountExternalId();
}
