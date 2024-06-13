package pl.romczaj.marketnotes.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.application.subtask.LoadCampaignTask;
import pl.romczaj.marketnotes.common.ApplicationClock;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.domain.command.CreateStockCompanyNoteCommand;
import pl.romczaj.marketnotes.domain.model.StockCompany;
import pl.romczaj.marketnotes.domain.model.StockNote;
import pl.romczaj.marketnotes.infrastructure.in.rest.CompanyRestManagement;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.AddCompanyNoteRequest;
import pl.romczaj.marketnotes.infrastructure.in.rest.request.LoadCompanyRequest;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.GetCompanyCurrentValueResult;

@Component
@RequiredArgsConstructor
public class CompanyRestManagementProcess implements CompanyRestManagement {

    private final StockCompanyRepository stockCompanyRepository;
    private final ApplicationClock applicationClock;
    private final DataProviderPort dataProviderPort;
    private final LoadCampaignTask loadCampaignTask;

    @Override
    public void loadCompanies(LoadCompanyRequest loadCompanyRequest) {
        loadCompanyRequest.companies().forEach(loadCampaignTask::loadOne);
    }

    @Override
    public void addNote(AddCompanyNoteRequest addNoteRequest) {
        StockCompany stockCompany = stockCompanyRepository.findByExternalId(addNoteRequest.externalId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        GetCompanyCurrentValueResult companyCurrentValue = dataProviderPort.getCompanyCurrentValue(
                new DataProviderPort.GetCompanyCurrentValueCommand(stockCompany.dataProviderSymbol()
                ));

        StockNote stockNote = StockNote.createFrom(
                new CreateStockCompanyNoteCommand(
                        stockCompany.id(),
                        applicationClock.instant(),
                        companyCurrentValue.lastHistoricData(),
                        addNoteRequest.noteContent()
                )
        );

       stockCompanyRepository.saveNote(stockNote);
    }
}
