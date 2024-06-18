package pl.romczaj.marketnotes.stockmarket.application.subtask;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRequest.CompanyRequestModel;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;

import static pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@RequiredArgsConstructor
@Component
public class LoadCampaignTask {

    private final StockCompanyRepository stockCompanyRepository;
    private final DataProviderPort dataProviderPort;

    @Transactional
    @Async
    public void loadOne(CompanyRequestModel companyRequestModel) {
        //given
        StockCompanyExternalId stockCompanyExternalId = new StockCompanyExternalId(companyRequestModel.stockSymbol(), companyRequestModel.stockMarketSymbol());
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(stockCompanyExternalId)
                .map(c -> c.updateFrom(companyRequestModel))
                .orElseGet(() -> StockCompany.createFrom(companyRequestModel));

        DataProviderPort.GetCompanyDataResult getCompanyDataResult = dataProviderPort.getCompanyData(new DataProviderPort.GetCompanyDataCommand(
                stockCompany.stockCompanyExternalId(),
                stockCompany.dataProviderSymbol(),
                7,
                DAILY
        ));

        StockCompany updatedStockCompany = stockCompany.updateActualPrice(getCompanyDataResult.getLatest().closePrice());
        stockCompanyRepository.saveStockCompany(updatedStockCompany);

    }
}
