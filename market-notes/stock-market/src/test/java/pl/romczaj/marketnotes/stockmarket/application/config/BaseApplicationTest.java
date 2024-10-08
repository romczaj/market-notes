package pl.romczaj.marketnotes.stockmarket.application.config;

import lombok.Builder;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.stockmarket.application.CompanyRestManagementTask;
import pl.romczaj.marketnotes.stockmarket.application.RefreshCompanyStockDataTask;
import pl.romczaj.marketnotes.stockmarket.application.subtask.LoadCampaignSubtask;
import pl.romczaj.marketnotes.stockmarket.application.subtask.RefreshAnalyzedDataCompanySubtask;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.MockStockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.mockito.Mockito.spy;
import static pl.romczaj.marketnotes.common.clock.ApplicationClock.defaultApplicationClock;
import static pl.romczaj.marketnotes.stockmarket.application.config.DefaultValues.DEFAULT_DATA_PROVIDER;


public class BaseApplicationTest {

    protected final ApplicationClock applicationClock;
    protected final DataProviderPort dataProviderPort;

    protected final StockCompanyRepository stockCompanyRepository;
    protected final RefreshAnalyzedDataCompanySubtask refreshAnalyzedDataCompanySubTask;
    protected final LoadCampaignSubtask loadCampaignSubTask;
    protected final RefreshCompanyStockDataTask refreshCompanyStockDataTask;
    protected final CompanyRestManagementTask companyRestManagementTask;

    public BaseApplicationTest() {
        this(WithMockObjects.builder().build());
    }

    public BaseApplicationTest(WithMockObjects withMockObjects) {
        this.applicationClock = defaultIfNull(withMockObjects.applicationClock, defaultApplicationClock());

        this.dataProviderPort = defaultIfNull(withMockObjects.dataProviderPort, DEFAULT_DATA_PROVIDER);

        this.stockCompanyRepository = new MockStockCompanyRepository();
        this.refreshAnalyzedDataCompanySubTask = spy(new RefreshAnalyzedDataCompanySubtask(applicationClock, stockCompanyRepository, dataProviderPort));
        this.loadCampaignSubTask = new LoadCampaignSubtask(stockCompanyRepository, refreshAnalyzedDataCompanySubTask, dataProviderPort);
        this.refreshCompanyStockDataTask = spy(new RefreshCompanyStockDataTask(stockCompanyRepository, refreshAnalyzedDataCompanySubTask));
        this.companyRestManagementTask = new CompanyRestManagementTask(stockCompanyRepository, applicationClock, dataProviderPort, loadCampaignSubTask);
    }

    @Builder
    public record WithMockObjects(
            ApplicationClock applicationClock,
            DataProviderPort dataProviderPort
    ) {
    }
}