package pl.romczaj.marketnotes.internalapi;

import pl.romczaj.marketnotes.common.id.UserAccountExternalId;


public interface AuthenticationRetriever {

    LoggedUser loggedUser();

    record LoggedUser(
            String username,
            String email,
            UserAccountExternalId userAccountExternalId) {
    }

    default UserAccountExternalId userAccountExternalId(){
        return loggedUser().userAccountExternalId();
    }

}
