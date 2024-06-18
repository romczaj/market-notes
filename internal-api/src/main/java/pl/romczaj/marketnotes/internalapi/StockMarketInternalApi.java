package pl.romczaj.marketnotes.internalapi;

import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

public interface StockMarketInternalApi {

    StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId);

    record StockCompanyResponse(
            StockCompanyExternalId companyExternalId,
            Money actualPrice
    ) {
    }

    default void validateStockCompanyExists(StockCompanyExternalId companyExternalId) {
        getCompanyBySymbol(companyExternalId);
    }
}
