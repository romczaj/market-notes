package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response;

import java.time.Instant;

public record CompanyNoteResponse(
            Long id,
            Instant noteDate,
            Double price,
            String noteContent
    ) {
    }