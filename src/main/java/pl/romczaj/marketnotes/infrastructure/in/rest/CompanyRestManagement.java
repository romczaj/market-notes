package pl.romczaj.marketnotes.infrastructure.in.rest;

import pl.romczaj.marketnotes.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.LoadCompanyRequest;

public interface CompanyRestManagement {
    void loadCompanies(LoadCompanyRequest loadCompanyRequest);
    void addNote(AddCompanyNoteRequest addNoteRequest);

}
