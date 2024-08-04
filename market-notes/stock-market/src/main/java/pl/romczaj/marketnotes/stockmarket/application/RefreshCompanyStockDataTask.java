package pl.romczaj.marketnotes.stockmarket.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.stockmarket.application.subtask.RefreshAnalyzedDataCompanySubtask;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.job.RefreshCompaniesPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshCompanyStockDataTask implements RefreshCompaniesPort {

    private final StockCompanyRepository stockCompanyRepository;
    private final RefreshAnalyzedDataCompanySubtask refreshAnalyzedDataCompanySubTask;

    @Override
    public void refreshCompanyStockData() {
        stockCompanyRepository.findAll().forEach(refreshAnalyzedDataCompanySubTask::refreshCompanyStockData);
    }

}
