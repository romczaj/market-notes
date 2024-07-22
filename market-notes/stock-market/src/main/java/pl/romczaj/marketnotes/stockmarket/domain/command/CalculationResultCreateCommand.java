package pl.romczaj.marketnotes.stockmarket.domain.command;

import pl.romczaj.marketnotes.common.dto.CalculationResult;

import java.time.LocalDateTime;

public record CalculationResultCreateCommand(
        Long stockCompanyId,
        LocalDateTime calculationDate,
        CalculationResult calculationResult
) {
}
