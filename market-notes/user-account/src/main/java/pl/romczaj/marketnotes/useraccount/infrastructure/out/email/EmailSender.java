package pl.romczaj.marketnotes.useraccount.infrastructure.out.email;

import lombok.With;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;

import java.util.List;

public interface EmailSender {

    SendEmailResult sendEmail(SendEmailReportCommand sendEmailReportCommand);

    record SendEmailReportCommand(
            String emailAddress,
            List<CompanyUserNotification> companyUserNotifications
    ) {}

    record SendEmailResult(
            Boolean success,
            String htmlContentMessage,
            String emailSubject
    ) {
    }
}
