package pl.romczaj.marketnotes.infrastructure.in.rest.respose;

import pl.romczaj.marketnotes.common.StockCompanyExternalId;

public record StockCompanyBaseInfoResponse(
        Long id,
        StockCompanyExternalId stockCompanyExternalId,
        String dataProviderSymbol,
        String companyName
) {
}