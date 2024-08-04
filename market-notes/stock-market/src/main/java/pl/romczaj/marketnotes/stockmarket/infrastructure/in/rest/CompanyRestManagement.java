package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest;

import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRestModel;

public interface CompanyRestManagement {
    void loadCompanies(LoadCompanyRestModel loadCompanyRestModel);
    void addNote(AddCompanyNoteRequest addNoteRequest);
    LoadCompanyRestModel exportCompanies();
}
