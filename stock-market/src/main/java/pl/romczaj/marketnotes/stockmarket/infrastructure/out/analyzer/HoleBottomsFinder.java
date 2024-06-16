package pl.romczaj.marketnotes.stockmarket.infrastructure.out.analyzer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.HistoricData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HoleBottomsFinder implements AnalyzerPort {

    @Override
    public CalculationResult findHoleBottoms(CalculationCommand command) {
        List<HistoricData> holeBottoms = findHoleBottoms(command.historicData());
        return new CalculationResult(
                holeBottoms,
                countIncrease(command.historicData())
        );
    }

    IncreaseResult countIncrease(final List<HistoricData> historicData) {
        final Map<LocalDate, HistoricData> mapPriceValues = historicData.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(HistoricData::date, Function.identity()));
        final LocalDate today = mapPriceValues.keySet().stream().max(LocalDate::compareTo).orElseThrow(() -> new IllegalStateException("No data"));
        final HistoricData todayValue = findHistoricDataByDate(mapPriceValues, today);

        final LocalDate yesterday = today.minusDays(1);
        final LocalDate weekAgo = today.minusDays(7);
        final LocalDate monthAgo = today.minusDays(30);
        final LocalDate threeMonthsAgo = today.minusDays(90);
        final LocalDate sixMonthsAgo = today.minusDays(180);
        final LocalDate yearAgo = today.minusDays(365);
        final LocalDate twoYearsAgo = today.minusDays(730);


        return new IncreaseResult(
                countIncrease(findHistoricDataByDate(mapPriceValues, yesterday), todayValue),
                countIncrease(findHistoricDataByDate(mapPriceValues, weekAgo), todayValue),
                countIncrease(findHistoricDataByDate(mapPriceValues, monthAgo), todayValue),
                countIncrease(findHistoricDataByDate(mapPriceValues, threeMonthsAgo), todayValue),
                countIncrease(findHistoricDataByDate(mapPriceValues, sixMonthsAgo), todayValue),
                countIncrease(findHistoricDataByDate(mapPriceValues, yearAgo), todayValue),
                countIncrease(findHistoricDataByDate(mapPriceValues, twoYearsAgo), todayValue)
        );
    }

    List<HistoricData> findHoleBottoms(List<HistoricData> historicData) {
        List<HistoricData> holeBottoms = new ArrayList<>();

        for (int i = 1; i < historicData.size() - 1; i++) {
            HistoricData previous = historicData.get(i - 1);
            HistoricData current = historicData.get(i);
            HistoricData next = historicData.get(i + 1);

            if (current.closePrice() < previous.closePrice() && current.closePrice() < next.closePrice()) {
                holeBottoms.add(current);
            }
        }

        return holeBottoms;
    }

    HistoricData findHistoricDataByDate(final Map<LocalDate, HistoricData> mapPriceValues, final LocalDate dateWanted) {
        LocalDate date = dateWanted;
        HistoricData historicData = mapPriceValues.get(date);
        while (historicData == null && date.isAfter(dateWanted.minusDays(1))) {
            date = date.minusDays(1);
            historicData = mapPriceValues.get(date);
        }
        return historicData;
    }


    Double countIncrease(HistoricData first, HistoricData last) {

        if (first == null || last == null) {
            return 0.0;
        }

        BigDecimal firstValue = BigDecimal.valueOf(first.closePrice());
        BigDecimal lastValue = BigDecimal.valueOf(last.closePrice());

        BigDecimal result = lastValue.subtract(firstValue)
                .divide(firstValue, 2, RoundingMode.DOWN)
                .multiply(BigDecimal.valueOf(100));

        return result.doubleValue();
    }

}