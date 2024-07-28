package pl.romczaj.marketnotes.stockmarket.domain.model;

import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.stockmarket.domain.command.CalculationResultCreateCommand;

import java.time.LocalDateTime;

public record CalculationResultHistory(
        Long id,
        Long stockCompanyId,
        LocalDateTime calculationDate,
        CalculationResult calculationResult
) implements DomainModel {

    public static CalculationResultHistory create(CalculationResultCreateCommand command) {
        return new CalculationResultHistory(
                null,
                command.stockCompanyId(),
                command.calculationDate(),
                command.calculationResult()
        );
    }

}
