package pl.romczaj.marketnotes.stockmarket.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.stockmarket.application.subtask.RefreshCompanyTask;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.job.RefreshCompaniesPort;
import pl.romczaj.marketnotes.stockmarket.domain.StockCompanyRepository;

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
