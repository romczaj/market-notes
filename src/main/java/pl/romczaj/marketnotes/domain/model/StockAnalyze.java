package pl.romczaj.marketnotes.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.domain.command.StockSummaryCreateUpdateCommand;

public record StockAnalyze(
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

    public static StockAnalyze from(StockSummaryCreateUpdateCommand command) {
        return new StockAnalyze(
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

    public StockAnalyze updateFrom(StockSummaryCreateUpdateCommand command) {
        return withDailyIncrease(command.dailyIncrease())
                .withWeeklyIncrease(command.weeklyIncrease())
                .withMonthlyIncrease(command.monthlyIncrease())
                .withThreeMonthsIncrease(command.threeMonthsIncrease())
                .withSixMonthsIncrease(command.sixMonthsIncrease())
                .withYearlyIncrease(command.yearlyIncrease())
                .withTwoYearsIncrease(command.twoYearsIncrease());
    }


}
