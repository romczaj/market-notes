package pl.romczaj.marketnotes.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
    public enum IncreasePeriod {
        DAILY(1, DAYS) {
            @Override
            public LocalDate countDate(LocalDate today) {
                if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
                    return today.minusDays(3);
                }
                return today.minusDays(1);
            }
        },
        WEEK(1, WEEKS) {
            @Override
            public LocalDate countDate(LocalDate today) {
                return today.minusWeeks(1);
            }
        },
        TWO_WEEKS(2, WEEKS) {
            @Override
            public LocalDate countDate(LocalDate today) {
                return today.minusWeeks(2);
            }
        },
        MONTH(1, MONTHS) {
            @Override
            public LocalDate countDate(LocalDate today) {
                return today.minusMonths(1);
            }
        },
        THREE_MONTHS(3, MONTHS) {
            @Override
            public LocalDate countDate(LocalDate today) {
                return today.minusMonths(3);
            }
        },
        YEAR(1, YEARS) {
            @Override
            public LocalDate countDate(LocalDate today) {
                return today.minusYears(1);
            }
        },
        TWO_YEARS(2, YEARS) {
            @Override
            public LocalDate countDate(LocalDate today) {
                return today.minusYears(2);
            }
        };

        private final int minusValue;
        private final ChronoUnit unit;

        public abstract LocalDate countDate(LocalDate today);

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