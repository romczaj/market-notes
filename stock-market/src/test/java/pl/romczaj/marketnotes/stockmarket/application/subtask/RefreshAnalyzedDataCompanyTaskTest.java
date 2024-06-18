package pl.romczaj.marketnotes.stockmarket.application.subtask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.application.BaseApplicationTest;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockAnalyze;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;

import static pl.romczaj.marketnotes.common.dto.Money.ofPln;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class RefreshAnalyzedDataCompanyTaskTest extends BaseApplicationTest {

    @Test
    void shouldRefreshCompanyStockData() {
        //given
        StockCompany stockCompany = new StockCompany(1L,
                new StockCompanyExternalId("aaa", WSE), "Market 1", "a.1", ofPln(100.0));
        StockCompany updatedStockData = stockCompanyRepository.saveStockCompany(stockCompany);

        //when
        refreshAnalyzedDataCompanyTask.refreshCompanyStockData(stockCompany);
        //then
        StockAnalyze stockAnalyze = stockCompanyRepository.findSummaryByStockCompanyId(stockCompany.id()).get();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1.0, stockAnalyze.dailyIncrease()),
                () -> Assertions.assertEquals(2.0, stockAnalyze.weeklyIncrease()),
                () -> Assertions.assertEquals(3.0, stockAnalyze.monthlyIncrease()),
                () -> Assertions.assertEquals(4.0, stockAnalyze.threeMonthsIncrease()),
                () -> Assertions.assertEquals(5.0, stockAnalyze.sixMonthsIncrease()),
                () -> Assertions.assertEquals(6.0, stockAnalyze.yearlyIncrease()),
                () -> Assertions.assertEquals(7.0, stockAnalyze.twoYearsIncrease()),
                () -> Assertions.assertNotNull(updatedStockData.actualPrice())
        );
    }
}
