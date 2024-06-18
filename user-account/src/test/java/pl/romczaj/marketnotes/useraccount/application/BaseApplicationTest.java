package pl.romczaj.marketnotes.useraccount.application;

import org.junit.jupiter.api.BeforeEach;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.UserAccountRestManagement;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

public class BaseApplicationTest {

    protected ApplicationClock applicationClock;
    protected StockMarketInternalApi stockMarketInternalApi;
    protected UserAccountRepository userAccountRepository;
    protected UserAccountRestManagement userAccountRestManagement;

    @BeforeEach
    void setUp() {
        ApplicationTestConfiguration applicationTestConfiguration = new ApplicationTestConfiguration();

        this.applicationClock = applicationTestConfiguration.getApplicationClock();
        this.stockMarketInternalApi = applicationTestConfiguration.getStockMarketInternalApi();
        this.userAccountRepository = applicationTestConfiguration.getUserAccountRepository();
        this.userAccountRestManagement = applicationTestConfiguration.getUserAccountRestManagement();
    }
}
