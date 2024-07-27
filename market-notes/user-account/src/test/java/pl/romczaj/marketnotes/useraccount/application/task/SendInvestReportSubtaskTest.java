package pl.romczaj.marketnotes.useraccount.application.task;

import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.ArchivePrice;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.useraccount.domain.model.InvestReport;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class SendInvestReportSubtaskTest extends BaseApplicationTest {


    @Test
    void shouldSendReport() {
        //given
        UserAccount userAccount = new UserAccount(
                1l,
                UserAccountExternalId.generate(),
                "jankowalski",
                "user@mail.com"
        );
        userAccountRepository.saveUserAccount(userAccount);

        List<CompanyUserNotification> companyUserNotifications = List.of(
                new CompanyUserNotification("AAA company", new StockCompanyExternalId("AAA", WSE), List.of(ArchivePrice.BUY_STOP), true),
                new CompanyUserNotification("BBB company", new StockCompanyExternalId("BBB", WSE), List.of(ArchivePrice.BUY_STOP),  false)
        );

        //when
        sendInvestReportSubtask.sendReport(userAccount, companyUserNotifications);

        //then
        List<InvestReport> investReports = userAccountRepository.findInvestReportByUserAccountId(userAccount.id());
        assertAll(
                () -> assertEquals(1, investReports.size()),
                () -> verify(emailSender, times(1)).sendEmail(any())
        );

    }

}