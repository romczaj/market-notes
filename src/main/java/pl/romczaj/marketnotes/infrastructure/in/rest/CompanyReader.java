package pl.romczaj.marketnotes.infrastructure.in.rest;

import pl.romczaj.marketnotes.common.StockCompanyExternalId;
import pl.romczaj.marketnotes.infrastructure.in.rest.respose.FullCompanyResponse;

public interface CompanyReader {
    FullCompanyResponse getCompany(StockCompanyExternalId companyId);
}
