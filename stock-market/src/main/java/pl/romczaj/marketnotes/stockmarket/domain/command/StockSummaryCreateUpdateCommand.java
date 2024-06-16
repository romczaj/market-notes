package pl.romczaj.marketnotes.stockmarket.domain.command;

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
