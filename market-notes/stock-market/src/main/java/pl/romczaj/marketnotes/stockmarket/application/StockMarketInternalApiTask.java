package pl.romczaj.marketnotes.stockmarket.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.JpaStockCompanySummaryViewRepository;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanySummaryView;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockMarketInternalApiTask implements StockMarketInternalApi {

    private final StockCompanyRepository stockCompanyRepository;
    private final JpaStockCompanySummaryViewRepository jpaStockCompanySummaryViewRepository;

    @Override
    public List<StockCompanyResponse> findAll() {
        return jpaStockCompanySummaryViewRepository.findAll().stream().map(this::stockCompanyResponse).toList();
    }

    @Override
    public StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId) {
        return jpaStockCompanySummaryViewRepository.findByExternalId(companyExternalId)
                .map(this::stockCompanyResponse)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
    }

    @Override
    public void validateStockCompanyExists(StockCompanyExternalId companyExternalId) {
        stockCompanyRepository.findByExternalId(companyExternalId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
    }

    private StockCompanyResponse stockCompanyResponse(StockCompanySummaryView stockCompanySummaryView) {
        return new StockCompanyResponse(
                stockCompanySummaryView.getCompanyName(),
                stockCompanySummaryView.getExternalId(),
                new Money(
                        stockCompanySummaryView.getActualPrice(),
                        stockCompanySummaryView.getExternalId().stockMarketSymbol().getCurrency()),
                stockCompanySummaryView.getCalculationResult()
        );
    }
}
