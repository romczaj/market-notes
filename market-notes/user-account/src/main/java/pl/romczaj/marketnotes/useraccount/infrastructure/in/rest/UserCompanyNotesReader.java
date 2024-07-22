package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.response.UserCompanyNotesResponse;

public interface UserCompanyNotesReader {
    UserCompanyNotesResponse getCompanyNotes(StockCompanyExternalId stockCompanyExternalId);
}
