package pl.romczaj.marketnotes.stockmarket.domain.command;

import pl.romczaj.marketnotes.common.dto.HistoricData;

import java.time.Instant;

public record CreateStockCompanyNoteCommand(
        Long companyId,
        Instant noteDate,
        HistoricData lastHistoricData,
        String noteContent
) {
}
