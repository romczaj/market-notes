package pl.romczaj.marketnotes.useraccount.application.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.useraccount.domain.model.CompanyInvestGoal;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.romczaj.marketnotes.common.id.StockCompanyExternalId.wse;


class PrepareInvestReportDataSubtaskTest extends BaseApplicationTest {

    private static final String MBK = "MBK";
    private static final String CRD = "CRD";

    PrepareInvestReportDataSubtaskTest() {
        super(WithMockObjects.builder()
                .stockMarketInternalApi(new StockMarketInternalApi() {
                    @Override
                    public List<StockCompanyExternalId> findAll() {
                        return List.of(wse(MBK), wse(CRD));
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
                wse(MBK),
                150.0,
                120.0,
                10.0,
                10.0
        );

        userAccountRepository.saveCompanyInvestGoal(mbkInvest);

        CompanyInvestGoal crdInvest = new CompanyInvestGoal(
                3l,
                userAccount.id(),
                wse(CRD),
                150.0,
                120.0,
                10.0,
                10.0
        );

        userAccountRepository.saveCompanyInvestGoal(crdInvest);

        //when
        List<CompanyUserNotification> notifications = prepareInvestReportDataSubtask.prepare(userAccount);

        //then
        Assertions.assertAll(
                () -> verify(stockMarketInternalApi, times(1)).findAll(),
                () -> verify(stockMarketInternalApi, times(1)).getCompanyBySymbol(mbkInvest.stockCompanyExternalId()),
                () -> verify(stockMarketInternalApi, times(1)).getCompanyBySymbol(crdInvest.stockCompanyExternalId()),
                () -> Assertions.assertEquals(2, notifications.size())
        );



    }
}