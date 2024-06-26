package pl.romczaj.marketnotes.useraccount.domain.command;

import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;

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
