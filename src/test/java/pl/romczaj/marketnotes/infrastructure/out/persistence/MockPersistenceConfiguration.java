package pl.romczaj.marketnotes.infrastructure.out.persistence;

import pl.romczaj.marketnotes.domain.StockCompanyRepository;

public class MockPersistenceConfiguration {

    public static StockCompanyRepository stockCompanyRepository() {
        return new MockStockCompanyRepository();
    }
}
