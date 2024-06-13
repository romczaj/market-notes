package pl.romczaj.marketnotes.application.subtask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.application.ApplicationTestConfiguration;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.domain.model.StockCompany;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.LoadCompanyRequest.CompanyRequestModel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadCampaignTaskTest {

    ApplicationTestConfiguration applicationTestConfiguration = new ApplicationTestConfiguration();
    StockCompanyRepository stockCompanyRepository;
    LoadCampaignTask loadCampaignTask;

    @BeforeEach
    void setUp() {
        stockCompanyRepository = applicationTestConfiguration.getStockCompanyRepository();
        loadCampaignTask = applicationTestConfiguration.getLoadCampaignTask();
    }

    @Test
    void shouldLoadOne() {
        //given
        CompanyRequestModel companyRequestModel = new CompanyRequestModel("Company 1", "Symbol 1", "Market 1", "a.1");
        //when
        loadCampaignTask.loadOne(companyRequestModel);

        //then
        StockCompanyExternalId stockCompanyExternalId = new StockCompanyExternalId("Symbol 1", "Market 1");
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(stockCompanyExternalId).get();
        assertAll(
                () -> assertEquals(1, stockCompanyRepository.findAll().size()),
                () -> assertEquals("Company 1", stockCompany.companyName()),
                () -> assertEquals("Symbol 1", stockCompany.stockCompanyExternalId().stockSymbol()),
                () -> assertEquals("Market 1", stockCompany.stockCompanyExternalId().stockMarketSymbol()
        ));
    }

}
