package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

public record StockCompanyBaseInfoResponse(
        Long id,
        StockCompanyExternalId stockCompanyExternalId,
        String dataProviderSymbol,
        String companyName
) {
}