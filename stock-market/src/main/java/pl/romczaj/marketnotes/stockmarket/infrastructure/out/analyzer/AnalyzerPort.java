package pl.romczaj.marketnotes.stockmarket.infrastructure.out.analyzer;

import pl.romczaj.marketnotes.common.dto.HistoricData;

import java.util.List;

public interface AnalyzerPort {

    CalculationResult findHoleBottoms(CalculationCommand command);

    record CalculationCommand(
            List<HistoricData> historicData
    ) {
    }

    record CalculationResult(
            List<HistoricData> holeBottoms,
            IncreaseResult increaseResult
    ) {
    }

    record IncreaseResult(
            Double dailyIncrease,
            Double weeklyIncrease,
            Double monthlyIncrease,
            Double threeMonthsIncrease,
            Double sixMonthsIncrease,
            Double yearlyIncrease,
            Double twoYearsIncrease
    ) {
    }



}
