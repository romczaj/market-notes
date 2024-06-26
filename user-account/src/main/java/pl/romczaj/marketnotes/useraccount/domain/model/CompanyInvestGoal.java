package pl.romczaj.marketnotes.useraccount.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteCompanyInvestGoalRequest;

public record CompanyInvestGoal(
        Long id,
        Long userAccountId,
        StockCompanyExternalId stockCompanyExternalId,
        @With Double buyPrice,
        @With Double sellPrice
) {

    public static CompanyInvestGoal create(Long userAccountId, NoteCompanyInvestGoalRequest request) {
        return new CompanyInvestGoal(
                null,
                userAccountId,
                request.stockCompanyExternalId(),
                request.buyPrice(),
                request.sellPrice()
        );
    }

    public CompanyInvestGoal updatePrices(NoteCompanyInvestGoalRequest request) {
        return withBuyPrice(request.buyPrice())
                .withSellPrice(request.sellPrice());
    }

    public boolean archivedBuyPrice(Double yesterdayPrice, Double todayPrice) {
        return buyPrice < yesterdayPrice && buyPrice < todayPrice;
    }

    public boolean archivedSellPrice(Double yesterdayPrice, Double todayPrice) {
        return sellPrice > yesterdayPrice && sellPrice > todayPrice;
    }
}
