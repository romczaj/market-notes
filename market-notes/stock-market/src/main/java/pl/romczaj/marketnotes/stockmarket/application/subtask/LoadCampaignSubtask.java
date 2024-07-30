package pl.romczaj.marketnotes.stockmarket.application.subtask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest.CompanyRequestModel;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

import static java.util.Objects.nonNull;
import static pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoadCampaignSubtask {

    private final StockCompanyRepository stockCompanyRepository;
    private final RefreshAnalyzedDataCompanySubtask refreshAnalyzedDataCompanySubtask;

    @Async
    public void loadOne(CompanyRequestModel companyRequestModel) {
        //given
        StockCompanyExternalId stockCompanyExternalId = new StockCompanyExternalId(companyRequestModel.stockSymbol(), companyRequestModel.stockMarketSymbol());
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(stockCompanyExternalId)
                .map(c -> c.updateFrom(companyRequestModel))
                .orElseGet(() -> StockCompany.createFrom(companyRequestModel));


        if (nonNull(stockCompany.id())) {
            log.info("Company {} is refreshed", stockCompany.companyName());
        }

        StockCompany savedStockCompany = stockCompanyRepository.saveStockCompany(stockCompany);

        refreshAnalyzedDataCompanySubtask.refreshCompanyStockData(savedStockCompany);
    }
}
