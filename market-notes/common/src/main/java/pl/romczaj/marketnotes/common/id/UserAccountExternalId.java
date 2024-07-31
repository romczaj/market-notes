package pl.romczaj.marketnotes.common.id;

import lombok.NonNull;

import java.util.UUID;

public record UserAccountExternalId(
        @NonNull UUID accountId
) implements StringFormatField {
    public static UserAccountExternalId fromString(String accountId) {
        UUID accountIdUUID = UUID.fromString(accountId);
        return new UserAccountExternalId(accountIdUUID);
    }

    public static UserAccountExternalId generate() {
        return new UserAccountExternalId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return accountId.toString();
    }
}
