package pl.romczaj.marketnotes.stockmarket.application.subtask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.stockmarket.application.ApplicationTestConfiguration;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockAnalyze;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;

class RefreshCompanyTaskTest {

    ApplicationTestConfiguration applicationTestConfiguration = new ApplicationTestConfiguration();
    StockCompanyRepository stockCompanyRepository;
    RefreshCompanyTask refreshCompanyTask;


    @BeforeEach
    void setUp() {
        stockCompanyRepository = applicationTestConfiguration.getStockCompanyRepository();
        refreshCompanyTask = applicationTestConfiguration.getRefreshCompanyTask();
    }

    @Test
    void shouldRefreshCompanyStockData() {
        //given
        StockCompany stockCompany = new StockCompany(1L,
                new StockCompanyExternalId("aaa", "bbb"), "Market 1", "a.1");
        stockCompanyRepository.saveStockCompany(stockCompany);

        //when
        refreshCompanyTask.refreshCompanyStockData(stockCompany);
        //then
        StockAnalyze stockAnalyze = stockCompanyRepository.findSummaryByStockCompanyId(stockCompany.id()).get();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1.0, stockAnalyze.dailyIncrease()),
                () -> Assertions.assertEquals(2.0, stockAnalyze.weeklyIncrease()),
                () -> Assertions.assertEquals(3.0, stockAnalyze.monthlyIncrease()),
                () -> Assertions.assertEquals(4.0, stockAnalyze.threeMonthsIncrease()),
                () -> Assertions.assertEquals(5.0, stockAnalyze.sixMonthsIncrease()),
                () -> Assertions.assertEquals(6.0, stockAnalyze.yearlyIncrease()),
                () -> Assertions.assertEquals(7.0, stockAnalyze.twoYearsIncrease())
        );
    }
}
