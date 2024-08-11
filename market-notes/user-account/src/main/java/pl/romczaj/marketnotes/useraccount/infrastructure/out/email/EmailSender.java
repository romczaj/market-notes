package pl.romczaj.marketnotes.useraccount.infrastructure.out.email;


import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;

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
