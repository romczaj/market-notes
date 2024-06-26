package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;

import java.time.LocalDateTime;

import static org.hibernate.type.SqlTypes.JSON;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_company_calculation_result_history")
public class CalculationResultHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long stockCompanyId;
    private LocalDateTime calculationDate;
    @JdbcTypeCode(JSON)
    private CalculationResult calculationResult;

    public static CalculationResultHistoryEntity fromDomain(CalculationResultHistory calculationResultHistory) {
        return new CalculationResultHistoryEntity(
                calculationResultHistory.id(),
                calculationResultHistory.stockCompanyId(),
                calculationResultHistory.calculationDate(),
                calculationResultHistory.calculationResult()
        );
    }

    public CalculationResultHistory toDomain() {
        return new CalculationResultHistory(
                id,
                stockCompanyId,
                calculationDate,
                calculationResult
        );
    }
}