package pl.romczaj.marketnotes.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.application.subtask.RefreshCompanyTask;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.infrastructure.in.job.RefreshCompaniesPort;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshCompanyStockDataProcess implements RefreshCompaniesPort {

    private final StockCompanyRepository stockCompanyRepository;
    private final RefreshCompanyTask refreshCompanyTask;

    @Override
    public void refreshCompanyStockData() {
        stockCompanyRepository.findAll().forEach(refreshCompanyTask::refreshCompanyStockData);
    }

}
