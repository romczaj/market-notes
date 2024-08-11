package pl.romczaj.marketnotes.useraccount.domain.command;


import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;

import java.time.LocalDateTime;
import java.util.List;

public record CreateInvestReportCommand(
        Long userAccountId,
        LocalDateTime sendDate,
        List<CompanyUserNotification> companyUserNotifications,
        boolean successSend,
        String emailMessage,
        String emailSubject
) {

}
