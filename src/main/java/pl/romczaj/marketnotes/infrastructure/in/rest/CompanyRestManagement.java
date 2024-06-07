package pl.romczaj.marketnotes.infrastructure.in.rest;

public interface CompanyRestManagement {
    void loadCompanies(LoadCompanyRequest loadCompanyRequest);
    void addNote(AddCompanyNoteRequest addNoteRequest);

}
