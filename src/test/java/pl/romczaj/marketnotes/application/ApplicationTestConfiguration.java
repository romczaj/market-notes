package pl.romczaj.marketnotes.application;

import lombok.Builder;
import lombok.Getter;
import pl.romczaj.marketnotes.application.subtask.RefreshCompanyTask;
import pl.romczaj.marketnotes.application.subtask.LoadCampaignTask;
import pl.romczaj.marketnotes.common.ApplicationClock;
import pl.romczaj.marketnotes.common.HistoricData;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort;
import pl.romczaj.marketnotes.infrastructure.out.analyzer.AnalyzerPort.CalculationResult;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.infrastructure.out.persistence.MockStockCompanyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class ApplicationTestConfiguration {

    private final ApplicationClock applicationClock;
    private final DataProviderPort dataProviderPort;
    private final AnalyzerPort analyzerPort;

    private final StockCompanyRepository stockCompanyRepository;
    private final RefreshCompanyTask refreshCompanyTask;
    private final LoadCampaignTask loadCampaignTask;
    private final RefreshCompanyStockDataProcess refreshCompanyStockDataProcess;
    private final CompanyRestManagementProcess companyRestManagementProcess;

    public ApplicationTestConfiguration() {
        this(WithMockObjects.builder().build());
    }

    public ApplicationTestConfiguration(WithMockObjects withMockObjects) {
        this.applicationClock = Optional.ofNullable(withMockObjects.applicationClock).orElse(ApplicationClock.fromLocalDateTime(LocalDateTime.now()));

        this.dataProviderPort = Optional.ofNullable(withMockObjects.dataProviderPort).orElse(new DataProviderPort() {
            @Override
            public GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand) {
                return new GetCompanyDataResult(getCompanyDataCommand.dataProviderSymbol(),
                        List.of(
                                new HistoricData(applicationClock.today(), 100.0)
                        ));
            }

            @Override
            public GetCompanyCurrentValueResult getCompanyCurrentValue(GetCompanyCurrentValueCommand getCompanyCurrentValueCommand) {
                return new GetCompanyCurrentValueResult(getCompanyCurrentValueCommand.dataProviderSymbol(),
                        new HistoricData(applicationClock.today(), 100.0));
            }
        });

        this.analyzerPort = Optional.ofNullable(withMockObjects.analyzerPort)
                .orElse(calculationCommand -> new CalculationResult(
                        List.of(new HistoricData(applicationClock.today(), 100.0)),
                        new AnalyzerPort.IncreaseResult(
                                1.0,
                                2.0,
                                3.0,
                                4.0,
                                5.0,
                                6.0,
                                7.0)));

        this.stockCompanyRepository = new MockStockCompanyRepository();
        this.refreshCompanyTask = new RefreshCompanyTask(stockCompanyRepository, dataProviderPort, analyzerPort);
        this.loadCampaignTask = new LoadCampaignTask(refreshCompanyTask, stockCompanyRepository);
        this.refreshCompanyStockDataProcess = new RefreshCompanyStockDataProcess(stockCompanyRepository, refreshCompanyTask);
        this.companyRestManagementProcess = new CompanyRestManagementProcess(stockCompanyRepository, applicationClock, dataProviderPort, loadCampaignTask);
    }

    @Builder
    public record WithMockObjects(
            ApplicationClock applicationClock,
            DataProviderPort dataProviderPort,
            AnalyzerPort analyzerPort
    ) {
    }
}