package pl.romczaj.marketnotes.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.*;

public record CalculationResult(
        Double todayPrice,
        Double yesterdayPrice,
        List<IncreasePeriodResult> increasePeriodResults
) {

    public static CalculationResult empty(){
        return new CalculationResult(0.0, 0.0, List.of());
    }

    @RequiredArgsConstructor
    @Getter
    public enum IncreasePeriod {
        DAILY(1, DAYS),
        WEEK(1, WEEKS),
        TWO_WEEKS(2, WEEKS),
        MONTH(1, MONTHS),
        THREE_MONTHS(3, MONTHS),
        YEAR(1, YEARS),
        TWO_YEARS(2, YEARS);

        private final int minusValue;
        private final ChronoUnit unit;

    }

    public record IncreasePeriodResult(
            IncreasePeriod increasePeriod,
            Double increasePercent,
            Double highestPeriodValue,
            Double lowestPeriodValue,
            Boolean scoreHighestPeak,
            Boolean scoreLowestPeak

    ) {
    }

}