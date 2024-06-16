package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

public record AddCompanyNoteRequest(
        @NonNull StockCompanyExternalId externalId,
        @NotBlank String noteContent
) {
}
