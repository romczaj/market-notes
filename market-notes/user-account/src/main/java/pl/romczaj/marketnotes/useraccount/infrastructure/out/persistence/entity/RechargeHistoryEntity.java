package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.dto.Money.Currency;
import pl.romczaj.marketnotes.useraccount.domain.model.RechargeHistory;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account_recharge_history")
public class RechargeHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long userAccountId;
    private Double amount;
    @Enumerated(STRING)
    private Currency currency;
    private LocalDate rechargeDate;

    public static RechargeHistoryEntity fromDomain(RechargeHistory domain) {
        return new RechargeHistoryEntity(
                domain.id(),
                domain.userAccountId(),
                domain.chargedMoney().amount(),
                domain.chargedMoney().currency(),
                domain.rechargeDate()
        );
    }

    public RechargeHistory toDomain(){
        return new RechargeHistory(
                id,
                userAccountId,
                new Money(amount, currency),
                rechargeDate
        );
    }
}
