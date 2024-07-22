package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest;


import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompaniesSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanyDetailSummaryResponse;

public interface CompanyReader {
    CompaniesSummaryResponse getCompaniesSummary();

    CompanyDetailSummaryResponse getCompanyDetailSummary(StockCompanyExternalId stockCompanyExternalId);
}
