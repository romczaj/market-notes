package pl.romczaj.marketnotes.stockmarket.application.subtask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.domain.command.StockSummaryCreateUpdateCommand;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockAnalyze;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.analyzer.AnalyzerPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.analyzer.AnalyzerPort.CalculationCommand;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.analyzer.AnalyzerPort.IncreaseResult;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataCommand;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataResult;

import java.util.List;

import static pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshAnalyzedDataCompanyTask {

    private final StockCompanyRepository stockCompanyRepository;

    private final DataProviderPort dataProviderPort;
    private final AnalyzerPort analyzerPort;


    public void refreshCompanyStockData(StockCompany company) {
        GetCompanyDataCommand getCompanyDataCommand = new GetCompanyDataCommand(
                company.stockCompanyExternalId(),
                company.dataProviderSymbol(),
                1000,
                DAILY);
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