package pl.romczaj.marketnotes.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;

public record AddCompanyNoteRequest(
        @NonNull StockCompanyExternalId externalId,
        @NotBlank String noteContent
) {
}
