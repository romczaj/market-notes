package pl.romczaj.marketnotes.useraccount.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.useraccount.application.task.FilterInvestNotificationSubtask;
import pl.romczaj.marketnotes.useraccount.application.task.PrepareInvestReportDataSubtask;
import pl.romczaj.marketnotes.useraccount.application.task.SendInvestReportSubtask;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.job.UserReportSchedulePort;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitGroupInvestReportProcess implements UserReportSchedulePort {

    private final PrepareInvestReportDataSubtask prepareInvestReportDataSubtask;
    private final UserAccountRepository userAccountRepository;
    private final SendInvestReportSubtask sendInvestReportSubtask;

    @Override
    @Async
    public void prepareAndSend() {
        userAccountRepository.findAll().forEach(this::sendInvestReportForUser);
    }

    private void sendInvestReportForUser(UserAccount userAccount){
        List<CompanyUserNotification> companyUserNotifications = prepareInvestReportDataSubtask.prepare(userAccount);
        log.info("Prepared {} notifications for user {}", companyUserNotifications.size(), userAccount.username());
        List<CompanyUserNotification> filterSignificant = FilterInvestNotificationSubtask.filterSignificant(companyUserNotifications);
        log.info("After filter {} notifications is ready to send for user {}", filterSignificant.size(), userAccount.externalId());
        sendInvestReportSubtask.sendReport(userAccount, filterSignificant);
    }

}
