package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest;

import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest;

public interface CompanyRestManagement {
    void loadCompanies(LoadCompanyRequest loadCompanyRequest);
    void addNote(AddCompanyNoteRequest addNoteRequest);

}
