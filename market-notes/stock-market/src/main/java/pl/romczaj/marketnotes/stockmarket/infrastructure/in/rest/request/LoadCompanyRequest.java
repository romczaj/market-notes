package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.romczaj.marketnotes.common.dto.StockMarketSymbol;

import java.util.List;

public record LoadCompanyRequest(
        @NotEmpty List<@Valid CompanyRequestModel> companies
) {
    public record CompanyRequestModel(
            @NotBlank String companyName,
            @NotBlank String stockSymbol,
            @NotNull StockMarketSymbol stockMarketSymbol,
            @NotBlank String dataProviderSymbol
    ) {
    }
}
