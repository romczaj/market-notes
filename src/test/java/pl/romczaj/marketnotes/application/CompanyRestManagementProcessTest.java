package pl.romczaj.marketnotes.application;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.domain.model.StockCompany;
import pl.romczaj.marketnotes.domain.model.StockNote;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.LoadCompanyRequest;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.LoadCompanyRequest.CompanyRequestModel;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyRestManagementProcessTest {

    ApplicationTestConfiguration applicationTestConfiguration = new ApplicationTestConfiguration();
    private StockCompanyRepository stockCompanyRepository;
    private CompanyRestManagementProcess stockCompanyLoaderProcessor;

    @BeforeEach
    void setUp() {
        stockCompanyRepository = applicationTestConfiguration.getStockCompanyRepository();
        stockCompanyLoaderProcessor = applicationTestConfiguration.getCompanyRestManagementProcess();
    }

    @Test
    void shouldAddNote() {
        //given
        StockCompany stockCompany = new StockCompany(1L,
                new StockCompanyExternalId("aaa", "bbb"), "Market 1", "a.1");
        stockCompanyRepository.saveStockCompany(stockCompany);

        //when
        stockCompanyLoaderProcessor.addNote(
                new AddCompanyNoteRequest(stockCompany.stockCompanyExternalId(), "Note content")
        );

        //then
        List<StockNote> notes = stockCompanyRepository.findNotesByStockCompanyId(stockCompany.id());
        assertAll(
                () -> assertEquals(1, notes.size()),
                () -> assertEquals(100.0, notes.get(0).price()),
                () -> assertEquals("Note content", notes.get(0).noteContent())
        );
    }
}