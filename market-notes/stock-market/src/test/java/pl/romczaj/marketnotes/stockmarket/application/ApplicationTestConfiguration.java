package pl.romczaj.marketnotes.stockmarket.application;

import lombok.Builder;
import lombok.Getter;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.stockmarket.application.subtask.LoadCampaignTask;
import pl.romczaj.marketnotes.stockmarket.application.subtask.RefreshAnalyzedDataCompanyTask;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.MockStockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static pl.romczaj.marketnotes.common.dto.Money.ofPln;

@Getter
public class ApplicationTestConfiguration {

    private final ApplicationClock applicationClock;
    private final DataProviderPort dataProviderPort;

    private final StockCompanyRepository stockCompanyRepository;
    private final RefreshAnalyzedDataCompanyTask refreshAnalyzedDataCompanyTask;
    private final LoadCampaignTask loadCampaignTask;
    private final RefreshCompanyStockDataProcess refreshCompanyStockDataProcess;
    private final CompanyRestManagementProcess companyRestManagementProcess;

    public ApplicationTestConfiguration() {
        this(WithMockObjects.builder().build());
    }

    public ApplicationTestConfiguration(WithMockObjects withMockObjects) {
        this.applicationClock = Optional.ofNullable(withMockObjects.applicationClock).orElse(ApplicationClock.fromLocalDateTime(LocalDateTime.now()));

        this.dataProviderPort = Optional.ofNullable(withMockObjects.dataProviderPort).orElse(
                getCompanyDataCommand -> new DataProviderPort.GetCompanyDataResult(
                getCompanyDataCommand.dataProviderSymbol(),
                List.of(
                        new HistoricData(applicationClock.today(), ofPln(100.0)))
        ));

      //  this.stockCompanyCounter = new StockCompanyCounter(applicationClock);
        this.stockCompanyRepository = new MockStockCompanyRepository();
        this.refreshAnalyzedDataCompanyTask = new RefreshAnalyzedDataCompanyTask(applicationClock, stockCompanyRepository, dataProviderPort);
        this.loadCampaignTask = new LoadCampaignTask(stockCompanyRepository, dataProviderPort);
        this.refreshCompanyStockDataProcess = new RefreshCompanyStockDataProcess(stockCompanyRepository, refreshAnalyzedDataCompanyTask);
        this.companyRestManagementProcess = new CompanyRestManagementProcess(stockCompanyRepository, applicationClock, dataProviderPort, loadCampaignTask);
    }

    @Builder
    public record WithMockObjects(
            ApplicationClock applicationClock,
            DataProviderPort dataProviderPort
    ) {
    }
}