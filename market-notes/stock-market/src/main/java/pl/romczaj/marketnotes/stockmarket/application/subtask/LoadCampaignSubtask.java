package pl.romczaj.marketnotes.stockmarket.application.subtask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRestModel.CompanyRequestModel;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.CompanyExistsCommand;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.CompanyExistsResult;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoadCampaignSubtask {

    private final StockCompanyRepository stockCompanyRepository;
    private final RefreshAnalyzedDataCompanySubtask refreshAnalyzedDataCompanySubtask;
    private final DataProviderPort dataProviderPort;

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

        CompanyExistsResult companyExistsResult = dataProviderPort.companyExistsResult(
                new CompanyExistsCommand(
                    new StockCompanyExternalId(
                        companyRequestModel.stockSymbol(), companyRequestModel.stockMarketSymbol()),
                companyRequestModel.dataProviderSymbol())
        );

        if (companyExistsResult.companyExists()) {
            StockCompany savedStockCompany = stockCompanyRepository.saveStockCompany(stockCompany);
            log.info("Company {} has been loaded", savedStockCompany.companyName());
            refreshAnalyzedDataCompanySubtask.refreshCompanyStockData(savedStockCompany);
        } else {
            log.warn("Cannot saved company {} because it does not exist in data provider", stockCompany.companyName());
        }
    }
}
