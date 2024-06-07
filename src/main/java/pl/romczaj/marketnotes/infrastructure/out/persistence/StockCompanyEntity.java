package pl.romczaj.marketnotes.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.domain.StockCompany;
import pl.romczaj.marketnotes.domain.StockCompanyExternalId;

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
    private String externalId;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String stockSymbol;
    @Column(nullable = false)
    private String stockMarketSymbol;
    @Column(nullable = false)
    private String dataProviderSymbol;

    StockCompany toDomain() {
        return new StockCompany(
                id,
                new StockCompanyExternalId(stockSymbol, stockMarketSymbol),
                dataProviderSymbol,
                companyName
        );
    }

    static StockCompanyEntity fromDomain(StockCompany stockCompany) {
        return new StockCompanyEntity(
                stockCompany.id(),
                stockCompany.stockCompanyExternalId().toDatabaseColumn(),
                stockCompany.companyName(),
                stockCompany.stockCompanyExternalId().stockSymbol(),
                stockCompany.stockCompanyExternalId().stockMarketSymbol(),
                stockCompany.dataProviderSymbol()
        );
    }
}
