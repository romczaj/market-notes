package pl.romczaj.marketnotes.infrastructure.in.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record AddCompanyNoteRequest(
        @NonNull Long companyId,
        @NotBlank String noteContent
) {
}
