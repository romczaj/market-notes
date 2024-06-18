package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.dto.Money.Currency;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.persistance.StockCompanyExternalIdDatabaseConverter;
import pl.romczaj.marketnotes.useraccount.common.StockOperation;
import pl.romczaj.marketnotes.useraccount.domain.model.BuySellHistory;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account_buy_sell_history")
public class BuySellHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long userAccountId;
    @Convert(converter = StockCompanyExternalIdDatabaseConverter.class)
    private StockCompanyExternalId stockCompanyExternalId;
    @Enumerated(EnumType.STRING)
    private StockOperation stockOperation;
    private Double operationAmount;
    @Enumerated(EnumType.STRING)
    private Currency operationCurrency;
    private LocalDate operationDate;

    public static BuySellHistoryEntity fromDomain(BuySellHistory domain) {
        return new BuySellHistoryEntity(
                domain.id(),
                domain.userAccountId(),
                domain.stockCompanyExternalId(),
                domain.stockOperation(),
                domain.operationPrice().amount(),
                domain.operationPrice().currency(),
                domain.operationDate()
        );
    }

    public BuySellHistory toDomain(){
        return new BuySellHistory(
                id,
                userAccountId,
                stockCompanyExternalId,
                stockOperation,
                new Money(operationAmount, operationCurrency),
                operationDate
        );
    }
}
