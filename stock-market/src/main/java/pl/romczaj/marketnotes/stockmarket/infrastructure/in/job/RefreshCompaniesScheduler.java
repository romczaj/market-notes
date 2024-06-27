package pl.romczaj.marketnotes.stockmarket.infrastructure.in.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class RefreshCompaniesScheduler {

    private final RefreshCompaniesPort refreshCompaniesPort;


    @Scheduled(cron = "${refresh-companies.cron}")
    void refreshCompanies() {
        log.info("Run refreshCompanies");
        refreshCompaniesPort.refreshCompanyStockData();
    }
}
