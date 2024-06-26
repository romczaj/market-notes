package pl.romczaj.marketnotes.stockmarket.application;

import org.junit.jupiter.api.BeforeEach;
import pl.romczaj.marketnotes.stockmarket.application.counter.StockCompanyCounter;
import pl.romczaj.marketnotes.stockmarket.application.subtask.LoadCampaignTask;
import pl.romczaj.marketnotes.stockmarket.application.subtask.RefreshAnalyzedDataCompanyTask;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

public class BaseApplicationTest {

    protected StockCompanyRepository stockCompanyRepository;
    protected CompanyRestManagementProcess stockCompanyLoaderProcessor;
    protected RefreshAnalyzedDataCompanyTask refreshAnalyzedDataCompanyTask;
    protected LoadCampaignTask loadCampaignTask;

    @BeforeEach
    void setUp() {
        ApplicationTestConfiguration applicationTestConfiguration = new ApplicationTestConfiguration();

        stockCompanyRepository = applicationTestConfiguration.getStockCompanyRepository();
        stockCompanyLoaderProcessor = applicationTestConfiguration.getCompanyRestManagementProcess();
        refreshAnalyzedDataCompanyTask = applicationTestConfiguration.getRefreshAnalyzedDataCompanyTask();
        loadCampaignTask = applicationTestConfiguration.getLoadCampaignTask();
    }
}
