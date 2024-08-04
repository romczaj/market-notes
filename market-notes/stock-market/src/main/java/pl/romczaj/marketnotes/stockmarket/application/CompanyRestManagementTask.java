package pl.romczaj.marketnotes.stockmarket.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.stockmarket.application.subtask.LoadCampaignSubtask;
import pl.romczaj.marketnotes.stockmarket.domain.command.CreateStockCompanyNoteCommand;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.CompanyRestManagement;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request.LoadCompanyRestModel;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataCommand;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.GetCompanyDataResult;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyRepository;

import java.util.List;

import static pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@Component
@RequiredArgsConstructor
public class CompanyRestManagementTask implements CompanyRestManagement {

    private final StockCompanyRepository stockCompanyRepository;
    private final ApplicationClock applicationClock;
    private final DataProviderPort dataProviderPort;
    private final LoadCampaignSubtask loadCampaignSubTask;

    @Override
    public void loadCompanies(LoadCompanyRestModel loadCompanyRestModel) {
        loadCompanyRestModel.companies().forEach(loadCampaignSubTask::loadOne);
    }

    @Override
    public void addNote(AddCompanyNoteRequest addNoteRequest) {
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(addNoteRequest.externalId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        GetCompanyDataResult getCompanyDataResult = dataProviderPort.getCompanyData(new GetCompanyDataCommand(
                stockCompany.stockCompanyExternalId(),
                stockCompany.dataProviderSymbol(),
                7,
                DAILY));

        StockNote stockNote = StockNote.createFrom(
                new CreateStockCompanyNoteCommand(
                        stockCompany.id(),
                        applicationClock.instant(), getCompanyDataResult.getLatest(),
                        addNoteRequest.noteContent()
                )
        );
        StockCompany updatedStockCompany = stockCompany.updateActualPrice(getCompanyDataResult.getLatest().closePrice());
        stockCompanyRepository.saveStockCompany(updatedStockCompany);
        stockCompanyRepository.saveNote(stockNote);
    }

    @Override
    public LoadCompanyRestModel exportCompanies() {
        List<LoadCompanyRestModel.CompanyRequestModel> companyModels = stockCompanyRepository.findAll().stream()
                .map(c -> new LoadCompanyRestModel.CompanyRequestModel(
                        c.companyName(),
                        c.stockCompanyExternalId().stockSymbol(),
                        c.stockCompanyExternalId().stockMarketSymbol(),
                        c.dataProviderSymbol()
                )).toList();
        return new LoadCompanyRestModel(companyModels);
    }
}
