package pl.romczaj.marketnotes.useraccount.application.report.subtask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;
import pl.romczaj.marketnotes.useraccount.domain.command.CreateInvestReportCommand;
import pl.romczaj.marketnotes.useraccount.domain.model.InvestReport;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.email.EmailSender;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.email.EmailSender.SendEmailReportCommand;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.email.EmailSender.SendEmailResult;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendInvestReportSubtask {

    private final EmailSender emailSender;
    private final UserAccountRepository userAccountRepository;
    private final ApplicationClock applicationClock;

    public void sendReport(UserAccount userAccount, List<CompanyUserNotification> companyUserNotifications) {

        SendEmailResult sendEmailResult = emailSender.sendEmail(new SendEmailReportCommand(
                userAccount.email(),
                companyUserNotifications
        ));

        InvestReport investReport = InvestReport.create(
                new CreateInvestReportCommand(
                        userAccount.id(),
                        applicationClock.localDateTime(),
                        companyUserNotifications,
                        sendEmailResult.success(),
                        sendEmailResult.htmlContentMessage(),
                        sendEmailResult.emailSubject()
                )
        );

        userAccountRepository.saveInvestReport(investReport);
        log.info("Invest report sent to user with id: {}", userAccount.externalId());
    }
}
