package pl.romczaj.marketnotes.useraccount.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.common.price.ArchivePriceCommand;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteCompanyInvestGoalRequest;

public record CompanyInvestGoal(
        Long id,
        Long userAccountId,
        StockCompanyExternalId stockCompanyExternalId,
        @With Double buyStopPrice,
        @With Double sellStopPrice,
        @With Double buyLimitPrice,
        @With Double sellLimitPrice
) implements DomainModel {

    public static CompanyInvestGoal create(Long userAccountId, NoteCompanyInvestGoalRequest request) {
        return new CompanyInvestGoal(
                null,
                userAccountId,
                request.stockCompanyExternalId(),
                request.buyStopPrice(),
                request.sellStopPrice(),
                request.buyLimitPrice(),
                request.sellLimitPrice()
        );
    }

    public CompanyInvestGoal updatePrices(NoteCompanyInvestGoalRequest request) {
        return withBuyStopPrice(request.buyStopPrice())
                .withSellStopPrice(request.sellStopPrice())
                .withBuyLimitPrice(request.buyLimitPrice())
                .withSellLimitPrice(request.sellLimitPrice());
    }


    public ArchivePriceCommand toArchivePriceCommand(Double yesterdayPrice, Double todayPrice) {
        return new ArchivePriceCommand(
                        buyStopPrice,
                        sellStopPrice,
                        buyLimitPrice,
                        sellLimitPrice,
                        yesterdayPrice,
                        todayPrice
        );
    }
}
