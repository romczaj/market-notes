package pl.romczaj.marketnotes.stockmarket.application;


import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.AddCompanyNoteRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.romczaj.marketnotes.common.dto.Money.ofPln;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class CompanyRestManagementTaskTest extends BaseApplicationTest {


    @Test
    void shouldAddNote() {
        //given
        StockCompany stockCompany = new StockCompany(1L,
                new StockCompanyExternalId("aaa", WSE), "Market 1", "a.1", ofPln(100.0));
        stockCompanyRepository.saveStockCompany(stockCompany);

        //when
        companyRestManagementTask.addNote(
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