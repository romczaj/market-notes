package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompaniesSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanyDetailSummaryResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyRestManagement companyRestManagement;
    private final CompanyReader companyReader;


    @PostMapping("/admin/load-companies")
    public void loadCompanies(@RequestBody @Valid LoadCompanyRequest loadCompanyRequest) {
        companyRestManagement.loadCompanies(loadCompanyRequest);
    }

    @GetMapping("/companies-summary")
    public CompaniesSummaryResponse getCompaniesSummary() {
        return companyReader.getCompaniesSummary();
    }

    @GetMapping("/company-detail-summary")
    public CompanyDetailSummaryResponse getCompanyDetailSummary(StockCompanyExternalId stockCompanyExternalId) {
        log.info("stock id {}", stockCompanyExternalId);
        return companyReader.getCompanyDetailSummary(stockCompanyExternalId);
    }

}
