package pl.romczaj.marketnotes.domain;

import lombok.With;
import pl.romczaj.marketnotes.infrastructure.in.rest.LoadCompanyRequest.CompanyModel;

public record StockCompany(
        Long id,
        StockCompanyExternalId stockCompanyExternalId,
        @With String dataProviderSymbol,
        String companyName) {

    public static StockCompany createFrom(CompanyModel companyModel) {
        return new StockCompany(
                null,
                new StockCompanyExternalId(companyModel.stockSymbol(), companyModel.stockMarketSymbol()),
                companyModel.dataProviderSymbol(),
                companyModel.companyName()
        );
    }

    public StockCompany updateFrom(CompanyModel companyModel) {
        return withDataProviderSymbol(companyModel.dataProviderSymbol());
    }
}
