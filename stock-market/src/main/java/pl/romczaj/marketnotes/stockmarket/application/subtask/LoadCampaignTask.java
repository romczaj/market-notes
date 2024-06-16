package pl.romczaj.marketnotes.stockmarket.application.subtask;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest.CompanyRequestModel;

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
