package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotBlank;

public record AddAccountRequest(
        @NotBlank String username,
        @NotBlank String email
) {
}
