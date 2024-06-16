package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest;


import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.FullCompanyResponse;

public interface CompanyReader {
    FullCompanyResponse getCompany(StockCompanyExternalId companyId);
}
