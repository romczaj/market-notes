package pl.romczaj.marketnotes.useraccount.application;

import lombok.Builder;
import lombok.Getter;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.dto.StockMarketSymbol;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.UserAccountRestManagement;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.MockUserAccountRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

@Getter
public class ApplicationTestConfiguration {

    private final ApplicationClock applicationClock;
    private final StockMarketInternalApi stockMarketInternalApi;

    private final UserAccountRepository userAccountRepository;
    private final UserAccountRestManagement userAccountRestManagement;

    public ApplicationTestConfiguration() {
        this(WithMockObjects.builder().build());
    }

    public ApplicationTestConfiguration(WithMockObjects withMockObjects) {
        this.applicationClock = Optional.ofNullable(withMockObjects.applicationClock)
                .orElse(ApplicationClock.fromLocalDateTime(LocalDateTime.now()));
        this.stockMarketInternalApi = new StockMarketInternalApi() {
            @Override
            public List<StockCompanyExternalId> findAll() {
                return List.of(
                        new StockCompanyExternalId("ELK", WSE)
                );
            }

            @Override
            public StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId) {
                return new StockCompanyResponse(
                        companyExternalId, Money.ofPln(100.0), CalculationResult.empty());
            }
        };

        this.userAccountRepository = new MockUserAccountRepository();
        this.userAccountRestManagement = new UserAccountRestManagementProcess(stockMarketInternalApi, userAccountRepository);
    }

    @Builder
    public record WithMockObjects(
            ApplicationClock applicationClock,
            StockMarketInternalApi stockMarketInternalApi
    ) {
    }
}