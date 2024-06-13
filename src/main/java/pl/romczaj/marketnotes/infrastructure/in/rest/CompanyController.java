package pl.romczaj.marketnotes.infrastructure.in.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.LoadCompanyRequest;
import pl.romczaj.marketnotes.infrastructure.in.rest.respose.FullCompanyResponse;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRestManagement companyRestManagement;
    private final CompanyReader companyReader;


    @PostMapping("/load-companies")
    public void loadCompanies(@RequestBody @Valid LoadCompanyRequest loadCompanyRequest) {
        companyRestManagement.loadCompanies(loadCompanyRequest);
    }

    @PostMapping("/add-note")
    public void addNote(@RequestBody @Valid AddCompanyNoteRequest addNoteRequest) {
        companyRestManagement.addNote(addNoteRequest);
    }

    @GetMapping("/company/{companyId}")
    public FullCompanyResponse getCompany(@PathVariable("companyId") StockCompanyExternalId companyId) {
        return companyReader.getCompany(companyId);
    }

}
