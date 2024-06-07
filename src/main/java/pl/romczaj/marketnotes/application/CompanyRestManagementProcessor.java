package pl.romczaj.marketnotes.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.domain.StockCompany;
import pl.romczaj.marketnotes.domain.StockCompanyExternalId;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.infrastructure.in.rest.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.infrastructure.in.rest.CompanyRestManagement;
import pl.romczaj.marketnotes.infrastructure.in.rest.LoadCompanyRequest;

@Component
@RequiredArgsConstructor
public class CompanyRestManagementProcessor implements CompanyRestManagement {

    private final StockCompanyRepository stockCompanyRepository;
    //private final StockProviderInternalApi stockProviderInternalApi;
    //private final ApplicationClock applicationClock;
    @Override
    public void loadCompanies(LoadCompanyRequest loadCompanyRequest) {
        loadCompanyRequest.companies().forEach(this::loadOne);
    }

    @Override
    public void addNote(AddCompanyNoteRequest addNoteRequest) {
        stockCompanyRepository.findById(addNoteRequest.companyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));


    }

    private void loadOne(LoadCompanyRequest.CompanyModel companyModel) {
        StockCompanyExternalId stockCompanyExternalId = new StockCompanyExternalId(companyModel.stockSymbol(), companyModel.stockMarketSymbol());
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(stockCompanyExternalId)
                .map(c -> c.updateFrom(companyModel))
                .orElseGet(() -> StockCompany.createFrom(companyModel));

        stockCompanyRepository.saveSummary(stockCompany);
    }
}
