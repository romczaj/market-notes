package pl.romczaj.marketnotes.stockmarket.application.counter;

import lombok.RequiredArgsConstructor;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriodResult;
import pl.romczaj.marketnotes.common.dto.HistoricData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class StockCompanyCounter {

    private final List<HistoricData> sortedHistoricData;
    private final Map<LocalDate, HistoricData> historicDataMap;
    private final LocalDate today;

    //TODO refactor usage of historicDataMap
    public StockCompanyCounter(List<HistoricData> historicDataParam) {
        this.sortedHistoricData = historicDataParam.stream().sorted((Comparator.comparing(HistoricData::date))).toList();
        this.historicDataMap = this.sortedHistoricData.stream().collect(Collectors.toMap(HistoricData::date, Function.identity()));
        this.today = historicDataMap.keySet().stream().max(LocalDate::compareTo).orElseThrow();
    }

    //TODO optimize
    public CalculationResult count() {
        return new CalculationResult(
                findHistoricDataByDate(today).closePrice().amount(),
                findHistoricDataByDate(today.minusDays(1)).closePrice().amount(),
                Stream.of(IncreasePeriod.values())
                        .map(this::countIncreasePeriodResult)
                        .toList());
    }

    IncreasePeriodResult countIncreasePeriodResult(IncreasePeriod increasePeriod) {
        LocalDate baseDate = today.minus(increasePeriod.getMinusValue(), increasePeriod.getUnit());
        HistoricData highestHistoricData = sortedHistoricData.stream()
                .filter(d -> d.date().equals(baseDate) || d.date().isAfter(baseDate))
                .max(Comparator.comparing(HistoricData::closePrice))
                .orElseThrow();

        HistoricData lowestHistoricData = sortedHistoricData.stream()
                .filter(d -> d.date().equals(baseDate) || d.date().isAfter(baseDate))
                .min(Comparator.comparing(HistoricData::closePrice))
                .orElseThrow();

        //TODO nulls
        return new IncreasePeriodResult(
                increasePeriod,
                countIncrease(baseDate),
                highestHistoricData.closePrice().amount(),
                lowestHistoricData.closePrice().amount(),
                highestHistoricData.date().equals(today),
                lowestHistoricData.date().equals(today)
        );
    }

    //TODO
    HistoricData findHistoricDataByDate(final LocalDate dateWanted) {

        HistoricData historicData = historicDataMap.get(dateWanted);
        if (nonNull(historicData)) {
            return historicData;
        }

        for (int i = 1; i < 7; i++) {
            LocalDate fixedDate = dateWanted.minusDays(i);
            historicData = historicDataMap.get(fixedDate);
            if (nonNull(historicData)) {
                return historicData;
            }

            fixedDate = dateWanted.plusDays(i);
            historicData = historicDataMap.get(fixedDate);
            if (nonNull(historicData)) {
                return historicData;
            }
        }

        return null;
    }

    Double countIncrease(LocalDate baseDate) {
        return countIncrease(baseDate, today);
    }

    Double countIncrease(LocalDate baseDate, LocalDate todayDate) {
        HistoricData baseData = findHistoricDataByDate(baseDate);
        HistoricData todayData = findHistoricDataByDate(todayDate);

        if (baseData == null || todayData == null) {
            return 0.0;
        }

        BigDecimal firstValue = BigDecimal.valueOf(baseData.closePrice().amount());
        BigDecimal lastValue = BigDecimal.valueOf(todayData.closePrice().amount());

        BigDecimal result = lastValue.subtract(firstValue)
                .divide(firstValue, 6, RoundingMode.DOWN)
                .multiply(BigDecimal.valueOf(100));

        return result.doubleValue();
    }
}