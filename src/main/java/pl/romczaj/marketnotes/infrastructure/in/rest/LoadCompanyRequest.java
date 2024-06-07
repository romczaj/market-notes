package pl.romczaj.marketnotes.infrastructure.in.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record LoadCompanyRequest(
        @NotEmpty List<@Valid CompanyModel> companies
) {
    public record CompanyModel(
            @NotBlank String companyName,
            @NotBlank String stockSymbol,
            @NotBlank String stockMarketSymbol,
            @NotBlank String dataProviderSymbol
    ) {
    }
}
