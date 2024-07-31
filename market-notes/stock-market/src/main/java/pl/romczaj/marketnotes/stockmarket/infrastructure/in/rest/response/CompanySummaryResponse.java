package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response;

import pl.romczaj.marketnotes.common.dto.Money.Currency;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.time.LocalDateTime;

public record CompanySummaryResponse(
            Long id,
            StockCompanyExternalId stockCompanyExternalId,
            String dataProviderSymbol,
            String companyName,
            LocalDateTime calculationDate,
            Double actualPrice,
            Currency currency,
            Double dailyIncrease,
            Double weekIncrease,
            Double twoWeekIncrease,
            Double monthIncrease,
            Double threeMonthsIncrease,
            Double yearIncrease,
            Double twoYearsIncrease
    ) {
    }