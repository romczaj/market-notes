package pl.romczaj.marketnotes.useraccount.domain.model;

import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

public record UserAccountStockGoal(
        Long id,
        Long userAccountId,
        Money goalValue,
        StockCompanyExternalId stockCompanyExternalId
) {
}
