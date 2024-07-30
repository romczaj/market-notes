package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.dto.StockMarketSymbol;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.persistance.StockCompanyExternalIdDatabaseConverter;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;

import java.util.Optional;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_company")
public class StockCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, unique = true)
    @Convert(converter = StockCompanyExternalIdDatabaseConverter.class)
    private StockCompanyExternalId externalId;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String stockSymbol;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockMarketSymbol stockMarketSymbol;
    @Column(nullable = false)
    private String dataProviderSymbol;
    @Column(nullable = false)
    private Double actualPrice;

    public StockCompany toDomain() {
        return new StockCompany(
                id,
                externalId,
                dataProviderSymbol,
                companyName,
                new Money(actualPrice, externalId.stockMarketSymbol().getCurrency())
        );
    }

    static StockCompanyEntity fromDomain(StockCompany stockCompany) {
        return new StockCompanyEntity(
                stockCompany.id(),
                stockCompany.stockCompanyExternalId(),
                stockCompany.companyName(),
                stockCompany.stockCompanyExternalId().stockSymbol(),
                stockCompany.stockCompanyExternalId().stockMarketSymbol(),
                stockCompany.dataProviderSymbol(),
                Optional.ofNullable(stockCompany.actualPrice()).map(Money::amount).orElse(null)
        );
    }
}
