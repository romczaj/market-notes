package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotNull;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

import java.time.LocalDate;

public record NoteAccountRechargeRequest(
        @NotNull UserAccountExternalId userAccountExternalId,
        @NotNull Money rechargeAmount,
        @NotNull LocalDate rechargeDate

) implements UserAccountOperationRequest {

}
