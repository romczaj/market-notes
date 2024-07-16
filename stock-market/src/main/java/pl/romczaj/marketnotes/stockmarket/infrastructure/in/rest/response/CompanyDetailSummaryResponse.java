package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response;

import pl.romczaj.marketnotes.common.dto.CalculationResult;

public record CompanyDetailSummaryResponse(
        CompanySummaryResponse basicSummary,
        CalculationResult calculationResult
) {
}
