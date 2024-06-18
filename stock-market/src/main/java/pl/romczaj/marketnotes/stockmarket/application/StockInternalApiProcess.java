package pl.romczaj.marketnotes.stockmarket.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

@Component
@RequiredArgsConstructor
public class StockInternalApiProcess implements StockMarketInternalApi {

    private final StockCompanyRepository stockCompanyRepository;

    @Override
    public StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId) {
        return stockCompanyRepository.findByExternalId(companyExternalId)
                .map(stockCompany ->
                        new StockCompanyResponse(
                                stockCompany.stockCompanyExternalId(),
                                stockCompany.actualPrice()))
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
    }
}
