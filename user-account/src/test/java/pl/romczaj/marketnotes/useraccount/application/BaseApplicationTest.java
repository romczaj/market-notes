package pl.romczaj.marketnotes.useraccount.application;

import lombok.Builder;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.application.task.PrepareInvestReportDataTask;
import pl.romczaj.marketnotes.useraccount.application.task.SendInvestReportTask;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.UserAccountRestManagement;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.email.EmailSender;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.MockUserAccountRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.spy;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

@Getter
@ExtendWith(MockitoExtension.class)
public class BaseApplicationTest {

    protected final ApplicationClock applicationClock;
    protected final StockMarketInternalApi stockMarketInternalApi;
    protected final EmailSender emailSender;

    protected final UserAccountRepository userAccountRepository;

    protected final PrepareInvestReportDataTask prepareInvestReportDataTask;
    protected final SendInvestReportTask sendInvestReportTask;

    protected final UserAccountRestManagement userAccountRestManagement;
    protected final InitGroupInvestRepostProcess initGroupInvestRepostProcess;

    public BaseApplicationTest() {
        this(WithMockObjects.builder().build());
    }

    public BaseApplicationTest(WithMockObjects withMockObjects) {
        //ports
        this.applicationClock = spy(Optional.ofNullable(withMockObjects.applicationClock)
                .orElse(ApplicationClock.fromLocalDateTime(LocalDateTime.now())));
        this.stockMarketInternalApi = spy( Optional.ofNullable(withMockObjects.stockMarketInternalApi)
                .orElse(new StockMarketInternalApi() {
                    @Override
                    public List<StockCompanyExternalId> findAll() {
                        return List.of(
                                new StockCompanyExternalId("ELK", WSE)
                        );
                    }

                    @Override
                    public StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId) {
                        return new StockCompanyResponse(
                                "Super comp",
                                companyExternalId,
                                Money.ofPln(100.0),
                                CalculationResult.empty());
                    }
                }));
        this.emailSender = spy (Optional.ofNullable(withMockObjects.emailSender)
                .orElse(new EmailSender() {
                    @Override
                    public SendEmailResult sendEmail(SendEmailReportCommand sendEmailReportCommand) {
                        return new EmailSender.SendEmailResult(true, "", "");
                    }
                }));

        //repo
        this.userAccountRepository = spy(new MockUserAccountRepository());

        //task
        this.prepareInvestReportDataTask = spy(new PrepareInvestReportDataTask(userAccountRepository, stockMarketInternalApi));
        this.sendInvestReportTask = spy(new SendInvestReportTask(emailSender, userAccountRepository, applicationClock));

        this.userAccountRestManagement = spy(new UserAccountRestManagementProcess(stockMarketInternalApi, userAccountRepository, applicationClock));
        this.initGroupInvestRepostProcess = spy(new InitGroupInvestRepostProcess(prepareInvestReportDataTask, userAccountRepository, sendInvestReportTask));
    }

    @Builder
    public record WithMockObjects(
            ApplicationClock applicationClock,
            StockMarketInternalApi stockMarketInternalApi,
            EmailSender emailSender
    ) {
    }
}