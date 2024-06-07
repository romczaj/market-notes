package pl.romczaj.marketnotes.infrastructure.in.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRestManagement companyRestManagement;

    @PostMapping("/load-companies")
    public void loadCompanies(@RequestBody LoadCompanyRequest loadCompanyRequest) {
        companyRestManagement.loadCompanies(loadCompanyRequest);
    }

    @PostMapping("/add-note")
    public void addNote(@RequestBody AddCompanyNoteRequest addNoteRequest) {
        companyRestManagement.addNote(addNoteRequest);
    }
}
