package pl.romczaj.marketnotes.application.subtask;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.domain.model.StockCompany;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.LoadCompanyRequest.CompanyRequestModel;

@RequiredArgsConstructor
@Component
public class LoadCampaignTask {

    private final RefreshCompanyTask refreshCompanyTask;
    private final StockCompanyRepository stockCompanyRepository;

    @Transactional
    @Async
    public void loadOne(CompanyRequestModel companyRequestModel) {
        //given
        StockCompanyExternalId stockCompanyExternalId = new StockCompanyExternalId(companyRequestModel.stockSymbol(), companyRequestModel.stockMarketSymbol());
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(stockCompanyExternalId)
                .map(c -> c.updateFrom(companyRequestModel))
                .orElseGet(() -> StockCompany.createFrom(companyRequestModel));

        StockCompany saveStockCompany = stockCompanyRepository.saveStockCompany(stockCompany);
        refreshCompanyTask.refreshCompanyStockData(saveStockCompany);
    }
}
