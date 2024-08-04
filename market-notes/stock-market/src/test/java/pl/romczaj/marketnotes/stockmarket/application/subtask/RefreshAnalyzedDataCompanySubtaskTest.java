package pl.romczaj.marketnotes.stockmarket.application.subtask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;

import static pl.romczaj.marketnotes.common.dto.Money.ofPln;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class RefreshAnalyzedDataCompanySubtaskTest extends BaseApplicationTest {

    @Test
    void shouldRefreshCompanyStockDataAsync() {
        //given
        StockCompany stockCompany = new StockCompany(1L,
                new StockCompanyExternalId("aaa", WSE), "Market 1", "a.1", ofPln(100.0));

        //when
        refreshAnalyzedDataCompanySubTask.refreshCompanyStockData(stockCompany);
        //then
        CalculationResultHistory calculationResultHistory = stockCompanyRepository.findNewestCalculationResult(stockCompany.id()).get();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(calculationResultHistory.calculationResult().todayPrice())
        );
    }
}
