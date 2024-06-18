package pl.romczaj.marketnotes.stockmarket.application.subtask;

import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.application.BaseApplicationTest;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class LoadCampaignTaskTest extends BaseApplicationTest {


    @Test
    void shouldLoadOne() {
        //given
        LoadCompanyRequest.CompanyRequestModel companyRequestModel = new LoadCompanyRequest.CompanyRequestModel("Company 1", "Symbol 1", WSE, "a.1");
        //when
        loadCampaignTask.loadOne(companyRequestModel);

        //then
        StockCompanyExternalId stockCompanyExternalId = new StockCompanyExternalId("Symbol 1", WSE);
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(stockCompanyExternalId).get();
        assertAll(
                () -> assertEquals(1, stockCompanyRepository.findAll().size()),
                () -> assertEquals("Company 1", stockCompany.companyName()),
                () -> assertEquals("Symbol 1", stockCompany.stockCompanyExternalId().stockSymbol()),
                () -> assertEquals(WSE, stockCompany.stockCompanyExternalId().stockMarketSymbol()
        ));
    }

}
