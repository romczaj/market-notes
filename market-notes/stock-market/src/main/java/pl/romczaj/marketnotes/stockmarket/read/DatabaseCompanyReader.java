package pl.romczaj.marketnotes.stockmarket.read;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriodResult;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.CompanyReader;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompaniesSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanyDetailSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanySummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.JpaStockCompanySummaryViewRepository;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanySummaryView;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class DatabaseCompanyReader implements CompanyReader {

    private final JpaStockCompanySummaryViewRepository jpaStockCompanySummaryViewRepository;

    @Override
    public CompaniesSummaryResponse getCompaniesSummary() {
        return new CompaniesSummaryResponse(
                jpaStockCompanySummaryViewRepository.findAll().stream().map(this::toResponse).toList()
        );
    }

    @Override
    public CompanyDetailSummaryResponse getCompanyDetailSummary(StockCompanyExternalId stockCompanyExternalId) {
        return jpaStockCompanySummaryViewRepository.findByExternalId(stockCompanyExternalId)
                .map(view -> new CompanyDetailSummaryResponse(
                        toResponse(view),
                        view.getCalculationResult()))
                .orElseThrow();
    }

    private CompanySummaryResponse toResponse(StockCompanySummaryView view) {
        List<IncreasePeriodResult> increasePeriodResults = view.getCalculationResult().increasePeriodResults();
        return new CompanySummaryResponse(
                view.getId(),
                view.getExternalId(),
                view.getExternalId().stockMarketSymbol().getCountry(),
                view.getDataProviderSymbol(),
                view.getCompanyName(),
                view.getCalculationDate(),
                view.getCalculationResult().todayPrice(),
                view.getExternalId().stockMarketSymbol().getCurrency(),
                retrieveIncrease(increasePeriodResults, IncreasePeriod.DAILY),
                retrieveIncrease(increasePeriodResults, IncreasePeriod.WEEK),
                retrieveIncrease(increasePeriodResults, IncreasePeriod.TWO_WEEKS),
                retrieveIncrease(increasePeriodResults, IncreasePeriod.MONTH),
                retrieveIncrease(increasePeriodResults, IncreasePeriod.THREE_MONTHS),
                retrieveIncrease(increasePeriodResults, IncreasePeriod.YEAR),
                retrieveIncrease(increasePeriodResults, IncreasePeriod.TWO_YEARS)
        );
    }

    private Double retrieveIncrease(List<IncreasePeriodResult> increasePeriodResults, IncreasePeriod increasePeriod) {
        return increasePeriodResults.stream()
                .filter(c -> c.increasePeriod() == increasePeriod)
                .findFirst()
                .map(IncreasePeriodResult::increasePercent)
                .orElse(0.0);
    }
}
