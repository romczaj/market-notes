package pl.romczaj.marketnotes.common.id;

import java.util.UUID;

public record UserAccountExternalId(
        UUID accountId
) {
    public static UserAccountExternalId fromString(String accountId) {
        UUID accountIdUUID = UUID.fromString(accountId);
        return new UserAccountExternalId(accountIdUUID);
    }

    public static UserAccountExternalId generate() {
        return new UserAccountExternalId(UUID.randomUUID());
    }
}
