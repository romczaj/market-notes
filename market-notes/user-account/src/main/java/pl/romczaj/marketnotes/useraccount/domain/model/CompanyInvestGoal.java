package pl.romczaj.marketnotes.useraccount.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.dto.ArchivePrice;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteCompanyInvestGoalRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public List<ArchivePrice> archivePrices(Double yesterdayPrice, Double todayPrice) {
        List<ArchivePrice> result = new ArrayList<>();
        if (archiveSellLimitPrice(yesterdayPrice, todayPrice)) {
            result.add(ArchivePrice.SELL_LIMIT);
        }
        if (archiveBuyLimitPrice(yesterdayPrice, todayPrice)) {
            result.add(ArchivePrice.BUY_LIMIT);
        }
        if (archiveSellStopPrice(yesterdayPrice, todayPrice)) {
            result.add(ArchivePrice.SELL_STOP);
        }
        if (archiveBuyStopPrice(yesterdayPrice, todayPrice)) {
            result.add(ArchivePrice.BUY_STOP);
        }
        return result;
    }

    public boolean archiveBuyStopPrice(Double yesterdayPrice, Double todayPrice) {
        return Optional.ofNullable(buyStopPrice)
                .filter(p -> yesterdayPrice < p && p < todayPrice)
                .isPresent();
    }

    public boolean archiveSellStopPrice(Double yesterdayPrice, Double todayPrice) {
        return Optional.ofNullable(sellStopPrice)
                .filter(p -> yesterdayPrice > p && p > todayPrice)
                .isPresent();
    }

    public boolean archiveBuyLimitPrice(Double yesterdayPrice, Double todayPrice) {
        return Optional.ofNullable(buyLimitPrice)
                .filter(p -> yesterdayPrice > p && p > todayPrice)
                .isPresent();
    }

    public boolean archiveSellLimitPrice(Double yesterdayPrice, Double todayPrice) {
        return Optional.ofNullable(sellLimitPrice)
                .filter(p -> yesterdayPrice < p && p < todayPrice)
                .isPresent();
    }

}
