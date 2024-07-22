package pl.romczaj.marketnotes.useraccount.infrastructure.out.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;

import java.util.List;

@Component
@Slf4j
public class PhysicalEmailSender implements EmailSender{

    private static final String DEFAULT_EMAIL_SUBJECT = "Daily invest report";
    private final String username;

    private final ObjectMapper objectMapper;

    private final JavaMailSender mailSender;
    @Override
    public SendEmailResult sendEmail(SendEmailReportCommand sendEmailReportCommand) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(sendEmailReportCommand.emailAddress());
        message.setSubject(DEFAULT_EMAIL_SUBJECT);
        message.setText(buildMessage(sendEmailReportCommand.companyUserNotifications()));

        try {
            mailSender.send(message);
            return new SendEmailResult(true, message.getText(), message.getSubject());
        } catch (Exception e) {
            log.error("Error during sending email", e);
            return new SendEmailResult(false, message.getText(), message.getSubject());
        }
    }

    private String buildMessage(List<CompanyUserNotification> companyUserNotifications) {
        try {
            return String.format(
                    "companies report %s",
                    objectMapper.writeValueAsString(companyUserNotifications)
            );
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error during building message", e);
        }
    }

    public PhysicalEmailSender(@Value("${spring.mail.username}") String username,
                               ObjectMapper objectMapper, JavaMailSender mailSender) {
        this.username = username;
        this.objectMapper = objectMapper;
        this.mailSender = mailSender;
    }

}
