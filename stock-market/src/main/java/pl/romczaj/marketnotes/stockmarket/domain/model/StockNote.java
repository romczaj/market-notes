package pl.romczaj.marketnotes.stockmarket.domain.model;

import pl.romczaj.marketnotes.stockmarket.domain.command.CreateStockCompanyNoteCommand;

import java.time.Instant;
import java.time.LocalDate;

public record StockNote(
    Long id,
    Long stockCompanyId,
    Instant noteDate,
    LocalDate priceDate,
    Double price,
    String noteContent
) {
    public static StockNote createFrom(CreateStockCompanyNoteCommand command) {
        return new StockNote(
            null,
            command.companyId(),
            command.noteDate(),
            command.lastHistoricData().date(),
            command.lastHistoricData().closePrice().amount(),
            command.noteContent()
        );
    }

}