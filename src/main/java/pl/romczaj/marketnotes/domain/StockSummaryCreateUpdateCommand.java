package pl.romczaj.marketnotes.domain;

public record StockSummaryCreateUpdateCommand(
        Long stockCompanyId,
        Double dailyIncrease,
        Double weeklyIncrease,
        Double monthlyIncrease,
        Double threeMonthsIncrease,
        Double sixMonthsIncrease,
        Double yearlyIncrease,
        Double twoYearsIncrease
) {
}
