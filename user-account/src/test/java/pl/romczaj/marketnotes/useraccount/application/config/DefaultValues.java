package pl.romczaj.marketnotes.useraccount.application.config;

import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

public class DefaultValues {

    public static final String DEFAULT_LOGGED_NAME = "Jan Kowalski";
    public static final String DEFAULT_LOGGED_EMAIL = "jankowalski@marketnotes.pl";
    public static final UserAccountExternalId DEFAULT_LOGGED_ACCOUNT_EXTERNAL_ID = UserAccountExternalId.generate();
}
