package pl.romczaj.marketnotes.useraccount.application.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;
import pl.romczaj.marketnotes.useraccount.domain.model.CompanyInvestGoal;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.romczaj.marketnotes.common.id.StockCompanyExternalId.wse;


class CompanyUserNotificationFactoryTest extends BaseApplicationTest {

    private static final StockCompanyExternalId MBK = wse("MBK");
    private static final StockCompanyExternalId CRD = wse("CRD");

    CompanyUserNotificationFactoryTest() {
        super(WithMockObjects.builder()
                .stockMarketInternalApi(new StockMarketInternalApi() {

                    @Override
                    public List<StockCompanyResponse> findAll() {
                        return List.of(getCompanyBySymbol(MBK), getCompanyBySymbol(CRD));
                    }

                    @Override
                    public StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId) {
                        return new StockCompanyResponse(
                                companyExternalId.stockSymbol(),
                                companyExternalId,
                                Money.ofPln(100.0),
                                new CalculationResult(100.0,
                                        80.0,
                                        List.of()
                                ));
                    }

                    @Override
                    public void validateStockCompanyExists(StockCompanyExternalId companyExternalId) {

                    }
                })
                .build());
    }

    @Test
    void shouldReturnNotificationCompany() {
        //given
        UserAccount userAccount = new UserAccount(
                1l,
                UserAccountExternalId.generate(),
                "jankowalski",
                "user@mail.com"
        );
        userAccountRepository.saveUserAccount(userAccount);

        CompanyInvestGoal mbkInvest = new CompanyInvestGoal(
                2l,
                userAccount.id(),
                MBK,
                150.0,
                120.0,
                10.0,
                10.0
        );

        userAccountRepository.saveCompanyInvestGoal(mbkInvest);

        CompanyInvestGoal crdInvest = new CompanyInvestGoal(
                3l,
                userAccount.id(),
                CRD,
                150.0,
                120.0,
                10.0,
                10.0
        );

        userAccountRepository.saveCompanyInvestGoal(crdInvest);

        //when
        List<CompanyUserNotification> notifications = companyUserNotificationFactory.prepare(userAccount);

        //then
        Assertions.assertAll(
                () -> verify(stockMarketInternalApi, times(1)).findAll(),
                () -> verify(stockMarketInternalApi, times(1)).getCompanyBySymbol(mbkInvest.stockCompanyExternalId()),
                () -> verify(stockMarketInternalApi, times(1)).getCompanyBySymbol(crdInvest.stockCompanyExternalId()),
                () -> Assertions.assertEquals(2, notifications.size())
        );



    }
}