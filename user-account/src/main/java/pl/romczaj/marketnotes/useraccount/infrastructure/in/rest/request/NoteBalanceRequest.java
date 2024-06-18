package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

import java.time.LocalDate;

public record NoteBalanceRequest(
        UserAccountExternalId userAccountExternalId,
        LocalDate accountBalanceDate,
        Money accountBalance
) implements UserAccountOperationRequest {
}
