package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockAnalyze;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_analyze")
public class StockAnalyzeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private Long stockCompanyId;
    private Double dailyIncrease;
    private Double weeklyIncrease;
    private Double monthlyIncrease;
    private Double threeMonthsIncrease;
    private Double sixMonthsIncrease;
    private Double yearlyIncrease;
    private Double twoYearsIncrease;

    public static StockAnalyzeEntity fromDomain(StockAnalyze stockAnalyze) {
        return new StockAnalyzeEntity(
                stockAnalyze.id(),
                stockAnalyze.stockCompanyId(),
                stockAnalyze.dailyIncrease(),
                stockAnalyze.weeklyIncrease(),
                stockAnalyze.monthlyIncrease(),
                stockAnalyze.threeMonthsIncrease(),
                stockAnalyze.sixMonthsIncrease(),
                stockAnalyze.yearlyIncrease(),
                stockAnalyze.twoYearsIncrease()
        );
    }
    public StockAnalyze toDomain() {
        return new StockAnalyze(
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