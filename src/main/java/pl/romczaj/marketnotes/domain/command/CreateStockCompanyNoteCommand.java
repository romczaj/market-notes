package pl.romczaj.marketnotes.domain.command;

import pl.romczaj.marketnotes.common.HistoricData;

import java.time.Instant;

public record CreateStockCompanyNoteCommand(
        Long companyId,
        Instant noteDate,
        HistoricData lastHistoricData,
        String noteContent
) {
}
