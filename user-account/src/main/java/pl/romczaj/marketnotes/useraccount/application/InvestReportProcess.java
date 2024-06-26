package pl.romczaj.marketnotes.useraccount.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.useraccount.application.task.FilterInvestNotificationTask;
import pl.romczaj.marketnotes.useraccount.application.task.PrepareInvestReportDataTask;
import pl.romczaj.marketnotes.useraccount.application.task.SendInvestReportTask;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.job.UserReportPort;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvestReportProcess implements UserReportPort {

    private final PrepareInvestReportDataTask prepareInvestReportDataTask;
    private final UserAccountRepository userAccountRepository;
    private final SendInvestReportTask sendInvestReportTask;
    @Override
    public void sendInvestReports() {
        userAccountRepository.findAll().forEach(this::sendInvestReportForUser);
    }

    private void sendInvestReportForUser(UserAccount userAccount){
        List<CompanyUserNotification> companyUserNotifications = prepareInvestReportDataTask.prepare(userAccount);
        log.info("Prepared {} notifications for user {}", companyUserNotifications.size(), userAccount.username());
        List<CompanyUserNotification> filterSignificant = FilterInvestNotificationTask.filterSignificant(companyUserNotifications);
        log.info("After filter {} notifications is ready to send for user {}", filterSignificant.size(), userAccount.externalId());
        sendInvestReportTask.sendReport(userAccount, filterSignificant);
    }

}
