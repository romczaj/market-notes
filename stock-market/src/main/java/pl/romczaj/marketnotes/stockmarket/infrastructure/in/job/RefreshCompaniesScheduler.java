package pl.romczaj.marketnotes.stockmarket.infrastructure.in.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshCompaniesScheduler {

    private final RefreshCompaniesPort refreshCompaniesPort;


  //  @Scheduled(cron = "0 * * * * *")
    public void refreshCompanies() {
        refreshCompaniesPort.refreshCompanyStockData();
    }
}
