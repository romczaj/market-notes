package pl.romczaj.marketnotes.infrastructure.in.rest.respose;

public record AnalyzeResponse(
            Long id,
            Double dailyIncrease,
            Double weeklyIncrease,
            Double monthlyIncrease,
            Double threeMonthsIncrease,
            Double sixMonthsIncrease,
            Double yearlyIncrease,
            Double twoYearsIncrease
    ) {
    }