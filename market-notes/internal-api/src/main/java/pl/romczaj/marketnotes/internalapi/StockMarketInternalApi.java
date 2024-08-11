package pl.romczaj.marketnotes.internalapi;

import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.util.List;

public interface StockMarketInternalApi {

    List<StockCompanyResponse> findAll();
    StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId);

    record StockCompanyResponse(
            String companyName,
            StockCompanyExternalId stockCompanyExternalId,
            Money actualPrice,
            CalculationResult calculationResult
    ) {
    }

    void validateStockCompanyExists(StockCompanyExternalId companyExternalId);
}
