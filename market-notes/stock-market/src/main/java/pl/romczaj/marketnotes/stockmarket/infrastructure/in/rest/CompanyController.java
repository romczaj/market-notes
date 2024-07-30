package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompaniesSummaryResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response.CompanyDetailSummaryResponse;

@RestController
@RequiredArgsConstructor
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

}
