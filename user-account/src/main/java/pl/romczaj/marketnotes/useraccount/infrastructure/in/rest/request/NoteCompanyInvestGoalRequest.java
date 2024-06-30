package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotNull;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

public record NoteCompanyInvestGoalRequest(
        @NotNull UserAccountExternalId userAccountExternalId,
        @NotNull StockCompanyExternalId stockCompanyExternalId,
        Double buyStopPrice,
        Double sellStopPrice,
        Double buyLimitPrice,
        Double sellLimitPrice

) implements UserAccountOperationRequest {
}
