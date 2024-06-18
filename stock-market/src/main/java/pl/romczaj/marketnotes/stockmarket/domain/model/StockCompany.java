package pl.romczaj.marketnotes.stockmarket.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest.CompanyRequestModel;

public record StockCompany(
        Long id,
        StockCompanyExternalId stockCompanyExternalId,
        @With String dataProviderSymbol,
        String companyName,
        @With Money actualPrice) {

    public static StockCompany createFrom(CompanyRequestModel companyRequestModel) {
        return new StockCompany(
                null,
                new StockCompanyExternalId(companyRequestModel.stockSymbol(), companyRequestModel.stockMarketSymbol()),
                companyRequestModel.dataProviderSymbol(),
                companyRequestModel.companyName(),
                null
        );
    }

    public StockCompany updateFrom(CompanyRequestModel companyRequestModel) {
        return withDataProviderSymbol(companyRequestModel.dataProviderSymbol());
    }

    public StockCompany updateActualPrice(Money actualPrice) {
        return withActualPrice(actualPrice);
    }
}
