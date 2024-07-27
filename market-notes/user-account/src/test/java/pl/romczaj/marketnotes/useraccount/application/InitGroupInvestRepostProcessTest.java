package pl.romczaj.marketnotes.useraccount.application;

import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.useraccount.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static pl.romczaj.marketnotes.useraccount.application.config.DefaultValues.*;

class InitGroupInvestRepostProcessTest extends BaseApplicationTest {

    @Test
    void shouldPrepareReport() {
        //given
        UserAccount userAccount = new UserAccount(
                1L,
                DEFAULT_LOGGED_ACCOUNT_EXTERNAL_ID,
                DEFAULT_LOGGED_NAME,
                DEFAULT_LOGGED_EMAIL
        );
        userAccountRepository.saveUserAccount(userAccount);

        //when
        initGroupInvestReportProcess.prepareAndSend();

        //then
        verify(prepareInvestReportDataSubtask, times(1)).prepare(eq(userAccount));
        verify(sendInvestReportSubtask, times(1)).sendReport(eq(userAccount), any());
    }

}