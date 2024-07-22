package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotNull;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

import java.time.LocalDate;

public record NoteBalanceRequest(
        @NotNull LocalDate accountBalanceDate,
        @NotNull Money accountBalance
) implements UserAccountOperationRequest {
}
