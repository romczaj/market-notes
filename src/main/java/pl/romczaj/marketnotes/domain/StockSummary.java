package pl.romczaj.marketnotes.domain;

import lombok.With;

public record StockSummary(
        Long id,
        @With Long stockCompanyId,
        @With Double dailyIncrease,
        @With Double weeklyIncrease,
        @With Double monthlyIncrease,
        @With Double threeMonthsIncrease,
        @With Double sixMonthsIncrease,
        @With Double yearlyIncrease,
        @With Double twoYearsIncrease
) {

    public static StockSummary from(StockSummaryCreateUpdateCommand command) {
        return new StockSummary(
                null,
                command.stockCompanyId(),
                command.dailyIncrease(),
                command.weeklyIncrease(),
                command.monthlyIncrease(),
                command.threeMonthsIncrease(),
                command.sixMonthsIncrease(),
                command.yearlyIncrease(),
                command.twoYearsIncrease()
        );
    }

    public StockSummary updateFrom(StockSummaryCreateUpdateCommand command) {
        return withDailyIncrease(command.dailyIncrease())
                .withWeeklyIncrease(command.weeklyIncrease())
                .withMonthlyIncrease(command.monthlyIncrease())
                .withThreeMonthsIncrease(command.threeMonthsIncrease())
                .withSixMonthsIncrease(command.sixMonthsIncrease())
                .withYearlyIncrease(command.yearlyIncrease())
                .withTwoYearsIncrease(command.twoYearsIncrease());
    }


}
