package pl.romczaj.marketnotes.infrastructure.in.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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
