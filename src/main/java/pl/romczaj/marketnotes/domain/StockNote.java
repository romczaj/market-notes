package pl.romczaj.marketnotes.domain;

import java.time.Instant;

public record StockNote(
    Long id,
    Long companyId,
    Instant noteDate,
    String noteContent
) {}