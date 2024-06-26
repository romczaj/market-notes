package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

public record NoteCompanyInvestGoalRequest(
        UserAccountExternalId userAccountExternalId,
        StockCompanyExternalId stockCompanyExternalId,
        Double buyPrice,
        Double sellPrice

) implements UserAccountOperationRequest {
}
