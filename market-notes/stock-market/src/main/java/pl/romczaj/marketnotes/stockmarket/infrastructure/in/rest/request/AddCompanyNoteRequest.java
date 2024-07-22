package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

public record AddCompanyNoteRequest(
        @NotNull StockCompanyExternalId externalId,
        @NotBlank String noteContent
) {
}
