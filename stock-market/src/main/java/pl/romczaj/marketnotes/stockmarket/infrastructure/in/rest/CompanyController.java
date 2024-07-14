package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.FullCompanyResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest;

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

    @GetMapping("/stock-market/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("sadas");
    }


}
