package pl.romczaj.marketnotes.stockmarket.read;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriodResult;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.CompanyReader;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompaniesSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanyDetailSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanySummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanySummaryResponse.IncreasePeriodSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.JpaStockCompanySummaryViewRepository;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanySummaryView;

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
        return new CompanySummaryResponse(
                view.getId(),
                view.getExternalId(),
                view.getExternalId().stockMarketSymbol().getCountry(),
                view.getDataProviderSymbol(),
                view.getCompanyName(),
                view.getCalculationDate(),
                view.getCalculationResult().todayPrice(),
                view.getExternalId().stockMarketSymbol().getCurrency(),
                view.getCalculationResult().increasePeriodResults().stream()
                        .map(this::toIncreasePeriodSummaryResponse).toList()
        );
    }

    private IncreasePeriodSummaryResponse toIncreasePeriodSummaryResponse(IncreasePeriodResult increasePeriodResult){
        return new IncreasePeriodSummaryResponse(
                increasePeriodResult.increasePeriod(),
                increasePeriodResult.increasePercent()
        );
    }
}
