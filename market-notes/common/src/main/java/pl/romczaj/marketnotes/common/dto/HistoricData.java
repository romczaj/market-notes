package pl.romczaj.marketnotes.common.dto;

import java.time.LocalDate;

public record HistoricData(
        LocalDate date,
        Money closePrice
) {
}
