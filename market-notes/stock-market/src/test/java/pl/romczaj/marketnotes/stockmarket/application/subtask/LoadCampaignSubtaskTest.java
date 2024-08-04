package pl.romczaj.marketnotes.stockmarket.application.subtask;

import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRestModel.CompanyRequestModel;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.romczaj.marketnotes.common.dto.Money.ofPln;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class LoadCampaignSubtaskTest extends BaseApplicationTest {

    private static final String COMPANY_NAME = "SuperCompany";
    private static final String COMPANY_STOCK_SYMBOL = "SUC";
    private static final String EXISTS_DATA_PROVIDER_SYMBOL = "SUC.WSE";

    LoadCampaignSubtaskTest() {
        super(WithMockObjects.builder()
                .dataProviderPort(new DataProviderPort() {
                    @Override
                    public GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand) {
                        return new GetCompanyDataResult(
                                getCompanyDataCommand.dataProviderSymbol(),
                                List.of(
                                        new HistoricData(LocalDate.now(), ofPln(100.0))
                                )
                        );
                    }

                    @Override
                    public CompanyExistsResult companyExistsResult(CompanyExistsCommand companyExistsCommand) {
                        if (companyExistsCommand.dataProviderSymbol().equals(EXISTS_DATA_PROVIDER_SYMBOL)) {
                            return new CompanyExistsResult(true);
                        }
                        return new CompanyExistsResult(false);
                    }
                })
                .build());
    }

    @Test
    void shouldLoadOne() {
        //given
        CompanyRequestModel companyRequestModel = new CompanyRequestModel(
                COMPANY_NAME, COMPANY_STOCK_SYMBOL, WSE, EXISTS_DATA_PROVIDER_SYMBOL);
        //when
        loadCampaignSubTask.loadOne(companyRequestModel);

        //then
        StockCompanyExternalId stockCompanyExternalId = new StockCompanyExternalId(COMPANY_STOCK_SYMBOL, WSE);
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(stockCompanyExternalId).get();
        assertAll(
                () -> assertEquals(1, stockCompanyRepository.findAll().size()),
                () -> assertEquals(COMPANY_NAME, stockCompany.companyName()),
                () -> assertEquals(COMPANY_STOCK_SYMBOL, stockCompany.stockCompanyExternalId().stockSymbol()),
                () -> assertEquals(WSE, stockCompany.stockCompanyExternalId().stockMarketSymbol()),
                () -> verify(refreshAnalyzedDataCompanySubTask, times(1)).refreshCompanyStockData(any())
        );
    }

    @Test
    void shouldNotLoadCompanyWhenSymbolNotExists() {
        //given
        CompanyRequestModel companyRequestModel = new CompanyRequestModel(
                COMPANY_NAME, COMPANY_STOCK_SYMBOL, WSE, "NOT_EXISTS_DATA_PROVIDER_SYMBOL");
        //when
        loadCampaignSubTask.loadOne(companyRequestModel);

        //then
        assertAll(
                () -> assertEquals(0, stockCompanyRepository.findAll().size()),
                () -> verify(refreshAnalyzedDataCompanySubTask, times(0)).refreshCompanyStockData(any())
        );
    }

}
