package pl.romczaj.marketnotes.useraccount.domain.model;

import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.useraccount.domain.command.CreateInvestReportCommand;

import java.time.LocalDateTime;
import java.util.List;

public record InvestReport(
        Long id,
        Long userAccountId,
        LocalDateTime sendDate,
        List<CompanyUserNotification> companyUserNotifications,
        boolean successSend,
        String emailMessage,
        String emailSubject
) implements DomainModel {
    public static InvestReport create(CreateInvestReportCommand command){
        return new InvestReport(
                null,
                command.userAccountId(),
                command.sendDate(),
                command.companyUserNotifications(),
                command.successSend(),
                command.emailMessage(),
                command.emailSubject()
        );
    }
}
