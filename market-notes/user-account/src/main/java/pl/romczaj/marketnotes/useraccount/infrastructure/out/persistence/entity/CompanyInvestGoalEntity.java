package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.persistance.StockCompanyExternalIdDatabaseConverter;
import pl.romczaj.marketnotes.useraccount.domain.model.CompanyInvestGoal;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account_company_invest_goal")
public class CompanyInvestGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long userAccountId;
    @Convert(converter = StockCompanyExternalIdDatabaseConverter.class)
    private StockCompanyExternalId stockCompanyExternalId;
    private Double buyStopPrice;
    private Double sellStopPrice;
    private Double buyLimitPrice;
    private Double sellLimitPrice;


    public static CompanyInvestGoalEntity fromDomain(CompanyInvestGoal domain) {
        return new CompanyInvestGoalEntity(
                domain.id(),
                domain.userAccountId(),
                domain.stockCompanyExternalId(),
                domain.buyStopPrice(),
                domain.sellStopPrice(),
                domain.buyLimitPrice(),
                domain.sellLimitPrice()
        );
    }

    public CompanyInvestGoal toDomain() {
        return new CompanyInvestGoal(
                id,
                userAccountId,
                stockCompanyExternalId,
                buyStopPrice,
                sellStopPrice,
                buyLimitPrice,
                sellLimitPrice
        );
    }

}
