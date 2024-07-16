package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import net.jcip.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.StockMarketSymbol;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.persistance.StockCompanyExternalIdDatabaseConverter;

import java.time.LocalDateTime;

import static org.hibernate.type.SqlTypes.JSON;

@Entity
@Immutable
@Getter
public class StockCompanySummaryView {

    @Id
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
    private LocalDateTime calculationDate;
    @JdbcTypeCode(JSON)
    private CalculationResult calculationResult;
}
