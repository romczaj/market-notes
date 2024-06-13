package pl.romczaj.marketnotes.infrastructure.in.rest.respose;

import java.time.Instant;

public record CompanyNoteResponse(
            Long id,
            Instant noteDate,
            Double price,
            String noteContent
    ) {
    }