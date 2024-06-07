package pl.romczaj.marketnotes.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.domain.StockCompany;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.domain.StockSummary;
import pl.romczaj.marketnotes.domain.StockSummaryCreateUpdateCommand;
import pl.romczaj.marketnotes.infrastructure.in.job.RefreshCompaniesPort;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort.CalculationCommand;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort.IncreaseResult;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataCommand;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataResult;

import static pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshCompanyStockDataProcess implements RefreshCompaniesPort {

    private final StockCompanyRepository stockCompanyRepository;

    private final DataProviderPort dataProviderPort;
    private final AnalyzerPort analyzerPort;

    @Override
    public void refreshCompanyStockData() {
        stockCompanyRepository.findAll().forEach(this::refreshCompanyStockData);
    }

    void refreshCompanyStockData(StockCompany company) {
        GetCompanyDataCommand getCompanyDataCommand = new GetCompanyDataCommand(company.dataProviderSymbol(), 1000, DAILY);
        GetCompanyDataResult getCompanyDataResult = dataProviderPort.getCompanyData(getCompanyDataCommand);

        CalculationCommand calculationCommand = new CalculationCommand(getCompanyDataResult.historicData());
        AnalyzerPort.CalculationResult result = analyzerPort.findHoleBottoms(calculationCommand);

        StockSummaryCreateUpdateCommand stockSummaryCreateUpdateCommand = getStockSummaryCreateUpdateCommand(company, result);

        StockSummary stockSummary = stockCompanyRepository.findSummaryByStockCompanyId(company.id())
                .map(s -> s.updateFrom(stockSummaryCreateUpdateCommand))
                .orElseGet(() -> StockSummary.from(stockSummaryCreateUpdateCommand));

        stockCompanyRepository.saveSummary(stockSummary);
    }

    private static StockSummaryCreateUpdateCommand getStockSummaryCreateUpdateCommand(StockCompany company, AnalyzerPort.CalculationResult result) {
        IncreaseResult increaseResult = result.increaseResult();
        return new StockSummaryCreateUpdateCommand(
                company.id(),
                increaseResult.dailyIncrease(),
                increaseResult.weeklyIncrease(),
                increaseResult.monthlyIncrease(),
                increaseResult.threeMonthsIncrease(),
                increaseResult.sixMonthsIncrease(),
                increaseResult.yearlyIncrease(),
                increaseResult.twoYearsIncrease()
        );
    }
}
