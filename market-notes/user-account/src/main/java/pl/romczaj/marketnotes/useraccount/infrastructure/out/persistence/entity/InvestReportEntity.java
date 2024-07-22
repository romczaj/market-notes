package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.useraccount.domain.model.InvestReport;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.type.SqlTypes.JSON;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account_invest_report")
public class InvestReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long userAccountId;
    private LocalDateTime sendDate;
    @JdbcTypeCode(JSON)
    private List<CompanyUserNotification> companyUserNotifications;
    private boolean successSend;
    @Column(columnDefinition="TEXT")
    private String emailMessage;
    private String emailSubject;

    public static InvestReportEntity fromDomain(InvestReport domain) {
        return new InvestReportEntity(
                domain.id(),
                domain.userAccountId(),
                domain.sendDate(),
                domain.companyUserNotifications(),
                domain.successSend(),
                domain.emailMessage(),
                domain.emailSubject()
        );
    }

    public InvestReport toDomain() {
        return new InvestReport(
                id,
                userAccountId,
                sendDate,
                companyUserNotifications,
                successSend,
                emailMessage,
                emailSubject
        );
    }
}