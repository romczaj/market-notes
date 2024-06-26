package pl.romczaj.marketnotes.internalapi;

import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.util.List;

public interface StockMarketInternalApi {

    List<StockCompanyExternalId> findAll();
    StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId);

    record StockCompanyResponse(
            StockCompanyExternalId stockCompanyExternalId,
            Money actualPrice,
            CalculationResult companyCounts
    ) {
    }


    default void validateStockCompanyExists(StockCompanyExternalId companyExternalId) {
        getCompanyBySymbol(companyExternalId);
    }
}
