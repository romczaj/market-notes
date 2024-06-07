package pl.romczaj.marketnotes.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.domain.StockSummary;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_summary")
public class StockSummaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long stockCompanyId;
    private Double dailyIncrease;
    private Double weeklyIncrease;
    private Double monthlyIncrease;
    private Double threeMonthsIncrease;
    private Double sixMonthsIncrease;
    private Double yearlyIncrease;
    private Double twoYearsIncrease;

    public static StockSummaryEntity fromDomain(StockSummary stockSummary) {
        return new StockSummaryEntity(
                stockSummary.id(),
                stockSummary.stockCompanyId(),
                stockSummary.dailyIncrease(),
                stockSummary.weeklyIncrease(),
                stockSummary.monthlyIncrease(),
                stockSummary.threeMonthsIncrease(),
                stockSummary.sixMonthsIncrease(),
                stockSummary.yearlyIncrease(),
                stockSummary.twoYearsIncrease()
        );
    }
    public StockSummary toDomain() {
        return new StockSummary(
                id,
                stockCompanyId,
                dailyIncrease,
                weeklyIncrease,
                monthlyIncrease,
                threeMonthsIncrease,
                sixMonthsIncrease,
                yearlyIncrease,
                twoYearsIncrease
        );
    }
}