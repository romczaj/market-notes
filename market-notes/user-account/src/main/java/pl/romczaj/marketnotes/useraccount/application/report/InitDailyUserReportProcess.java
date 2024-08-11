package pl.romczaj.marketnotes.useraccount.application.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.useraccount.application.report.subtask.FilterInvestNotificationSubtask;
import pl.romczaj.marketnotes.useraccount.application.report.subtask.CompanyUserNotificationFactory;
import pl.romczaj.marketnotes.useraccount.application.report.subtask.SendInvestReportSubtask;
import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.job.UserReportSchedulePort;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class InitDailyUserReportProcess implements UserReportSchedulePort {

    private static final FilterInvestNotificationSubtask filterInvestNotificationSubtask = new FilterInvestNotificationSubtask();

    private final CompanyUserNotificationFactory companyUserNotificationFactory;
    private final UserAccountRepository userAccountRepository;
    private final SendInvestReportSubtask sendInvestReportSubtask;

    @Override
    @Async
    public void prepareAndSend() {
        userAccountRepository.findAll().forEach(this::sendInvestReportForUser);
    }

    private void sendInvestReportForUser(UserAccount userAccount){
        List<CompanyUserNotification> companyUserNotifications = companyUserNotificationFactory.prepare(userAccount);
        log.info("Prepared {} company notifications for user {}", companyUserNotifications.size(), userAccount.username());
        List<CompanyUserNotification> filterSignificant = filterInvestNotificationSubtask.filterSignificant(companyUserNotifications);
        log.info("After filter {} notifications is ready to send for user {}", filterSignificant.size(), userAccount.externalId());
        sendInvestReportSubtask.sendReport(userAccount, filterSignificant);
    }

}
