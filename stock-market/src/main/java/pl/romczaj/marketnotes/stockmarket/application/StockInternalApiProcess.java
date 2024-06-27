package pl.romczaj.marketnotes.stockmarket.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockInternalApiProcess implements StockMarketInternalApi {

    private final StockCompanyRepository stockCompanyRepository;

    @Override
    public List<StockCompanyExternalId> findAll() {
        return stockCompanyRepository.findAll()
                .stream()
                .map(StockCompany::stockCompanyExternalId)
                .toList();
    }

    @Override
    public StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId) {
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(companyExternalId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        CalculationResult calculationResult = stockCompanyRepository.findNewestCalculationResult(stockCompany.id())
                .map(CalculationResultHistory::calculationResult)
                .orElse(CalculationResult.empty());

        return new StockCompanyResponse(
                stockCompany.companyName(),
                stockCompany.stockCompanyExternalId(),
                stockCompany.actualPrice(),
                calculationResult
        );

    }
}
