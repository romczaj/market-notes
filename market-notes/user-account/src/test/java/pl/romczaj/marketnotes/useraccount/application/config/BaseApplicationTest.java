package pl.romczaj.marketnotes.useraccount.application.config;

import lombok.Builder;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.internalapi.AuthenticationRetriever;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.application.InitGroupInvestReportProcess;
import pl.romczaj.marketnotes.useraccount.application.UserAccountRestManagementProcess;
import pl.romczaj.marketnotes.useraccount.application.task.PrepareInvestReportDataSubtask;
import pl.romczaj.marketnotes.useraccount.application.task.SendInvestReportSubtask;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.UserAccountRestManagement;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.email.EmailSender;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.MockUserAccountRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.mockito.Mockito.spy;
import static pl.romczaj.marketnotes.common.clock.ApplicationClock.defaultApplicationClock;
import static pl.romczaj.marketnotes.useraccount.application.config.DefaultValues.*;

public class BaseApplicationTest {

    protected final AuthenticationRetriever authenticationRetriever;
    protected final ApplicationClock applicationClock;
    protected final StockMarketInternalApi stockMarketInternalApi;
    protected final EmailSender emailSender;

    protected final UserAccountRepository userAccountRepository;

    protected final PrepareInvestReportDataSubtask prepareInvestReportDataSubtask;
    protected final SendInvestReportSubtask sendInvestReportSubtask;

    protected final UserAccountRestManagement userAccountRestManagement;
    protected final InitGroupInvestReportProcess initGroupInvestReportProcess;

    public BaseApplicationTest() {
        this(WithMockObjects.builder().build());
    }

    public BaseApplicationTest(WithMockObjects withMockObjects) {
        //ports
        this.authenticationRetriever = spy(DEFAULT_AUTHENTICATION_RETRIEVER);
        this.applicationClock = spy(defaultIfNull(withMockObjects.applicationClock, defaultApplicationClock()));
        this.stockMarketInternalApi = spy(defaultIfNull(withMockObjects.stockMarketInternalApi, DEFAULT_STOCK_MARKET_INTERNAL_API));
        this.emailSender = spy(defaultIfNull(withMockObjects.emailSender(), DEFAULT_EMAIL_SENDER));

        //repo
        this.userAccountRepository = spy(new MockUserAccountRepository());

        //task
        this.prepareInvestReportDataSubtask = spy(new PrepareInvestReportDataSubtask(userAccountRepository, stockMarketInternalApi));
        this.sendInvestReportSubtask = spy(new SendInvestReportSubtask(emailSender, userAccountRepository, applicationClock));

        this.userAccountRestManagement = spy(new UserAccountRestManagementProcess(stockMarketInternalApi, userAccountRepository, applicationClock, authenticationRetriever));
        this.initGroupInvestReportProcess = spy(new InitGroupInvestReportProcess(prepareInvestReportDataSubtask, userAccountRepository, sendInvestReportSubtask));
    }

    @Builder
    public record WithMockObjects(
            ApplicationClock applicationClock,
            StockMarketInternalApi stockMarketInternalApi,
            EmailSender emailSender
    ) {
    }
}