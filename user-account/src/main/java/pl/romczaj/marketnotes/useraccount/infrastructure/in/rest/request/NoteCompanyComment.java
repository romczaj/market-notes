package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

public record NoteCompanyComment(
        @NotNull UserAccountExternalId userAccountExternalId,
        @NotNull StockCompanyExternalId stockCompanyExternalId,
        @NotBlank String noteContent

) implements UserAccountOperationRequest {
}
