package pl.romczaj.marketnotes.common;

import java.time.LocalDate;

public record HistoricData(
        LocalDate date,
        Double closePrice
) {
}
