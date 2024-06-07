package pl.romczaj.marketnotes.application;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.infrastructure.in.rest.LoadCompanyRequest;

import java.util.Arrays;
import java.util.List;

import static pl.romczaj.marketnotes.infrastructure.out.persistence.MockPersistenceConfiguration.stockCompanyRepository;

class CompanyRestManagementProcessorTest {

    StockCompanyRepository stockCompanyRepository = stockCompanyRepository();
    CompanyRestManagementProcessor stockCompanyLoaderProcessor = new CompanyRestManagementProcessor(stockCompanyRepository);


    @Test
    void shouldLoadStockCompanies() {
        //given
        List<LoadCompanyRequest.CompanyModel> companies = Arrays.asList(
                new LoadCompanyRequest.CompanyModel("Company 1", "Symbol 1", "Market 1", "a.1"),
                new LoadCompanyRequest.CompanyModel("Company 2", "Symbol 2", "Market 2", "b.22")
        );
        LoadCompanyRequest loadCompanyRequest = new LoadCompanyRequest(companies);

        //when
        stockCompanyLoaderProcessor.loadCompanies(loadCompanyRequest);

        //then
        Assertions.assertEquals(2, stockCompanyRepository.findAll().size());
    }
}