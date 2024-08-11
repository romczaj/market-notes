package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response;

import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriodResult;
import pl.romczaj.marketnotes.common.dto.Country;
import pl.romczaj.marketnotes.common.dto.Money.Currency;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.time.LocalDateTime;
import java.util.List;

public record CompanySummaryResponse(
            Long id,
            StockCompanyExternalId stockCompanyExternalId,
            Country country,
            String dataProviderSymbol,
            String companyName,
            LocalDateTime calculationDate,
            Double actualPrice,
            Currency currency,
            List<IncreasePeriodSummaryResponse> increasePeriodResults
    ) {

    public record IncreasePeriodSummaryResponse(
            IncreasePeriod increasePeriod,
            Double increasePercent) {
    }
}