package pl.romczaj.marketnotes.stockmarket.application.subtask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.stockmarket.application.counter.StockCompanyCounter;
import pl.romczaj.marketnotes.stockmarket.domain.command.CalculationResultCreateCommand;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataCommand;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataResult;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

import java.util.List;

import static pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshAnalyzedDataCompanyTask {

    private final ApplicationClock applicationClock;
    private final StockCompanyRepository stockCompanyRepository;
    private final DataProviderPort dataProviderPort;

    @Async
    public void refreshCompanyStockData(StockCompany company) {
        GetCompanyDataCommand getCompanyDataCommand = new GetCompanyDataCommand(
                company.stockCompanyExternalId(),
                company.dataProviderSymbol(),
                1000,
                DAILY);
        GetCompanyDataResult getCompanyDataResult = dataProviderPort.getCompanyData(getCompanyDataCommand);

        List<HistoricData> historicData = getCompanyDataResult.historicData();

        StockCompanyCounter stockCompanyCounter = new StockCompanyCounter(historicData);
        CalculationResult result = stockCompanyCounter.count();

        CalculationResultCreateCommand calculationResultCreateCommand = new CalculationResultCreateCommand(
                company.id(),
                applicationClock.now(),
                result
        );

        CalculationResultHistory calculationResultHistory = CalculationResultHistory.create(calculationResultCreateCommand);

        StockCompany updatedStockCompany = company.updateActualPrice(getCompanyDataResult.getLatest().closePrice());
        stockCompanyRepository.saveStockCompany(updatedStockCompany);
        stockCompanyRepository.saveSummary(calculationResultHistory);
    }
}
