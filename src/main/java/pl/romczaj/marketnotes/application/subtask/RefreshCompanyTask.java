package pl.romczaj.marketnotes.application.subtask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.HistoricData;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.domain.command.StockSummaryCreateUpdateCommand;
import pl.romczaj.marketnotes.domain.model.StockAnalyze;
import pl.romczaj.marketnotes.domain.model.StockCompany;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort.CalculationCommand;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort.IncreaseResult;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataCommand;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataResult;

import java.util.List;

import static pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshCompanyTask {

    private final StockCompanyRepository stockCompanyRepository;

    private final DataProviderPort dataProviderPort;
    private final AnalyzerPort analyzerPort;

    @Async
    public void refreshCompanyStockData(StockCompany company) {
        GetCompanyDataCommand getCompanyDataCommand = new GetCompanyDataCommand(company.dataProviderSymbol(), 1000, DAILY);
        GetCompanyDataResult getCompanyDataResult = dataProviderPort.getCompanyData(getCompanyDataCommand);

        List<HistoricData> historicData = getCompanyDataResult.historicData();

        CalculationCommand calculationCommand = new CalculationCommand(historicData);
        AnalyzerPort.CalculationResult result = analyzerPort.findHoleBottoms(calculationCommand);

        StockSummaryCreateUpdateCommand stockSummaryCreateUpdateCommand = getStockSummaryCreateUpdateCommand(company, result);

        StockAnalyze stockAnalyze = stockCompanyRepository.findSummaryByStockCompanyId(company.id())
                .map(s -> s.updateFrom(stockSummaryCreateUpdateCommand))
                .orElseGet(() -> StockAnalyze.from(stockSummaryCreateUpdateCommand));

        stockCompanyRepository.saveSummary(stockAnalyze);
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
