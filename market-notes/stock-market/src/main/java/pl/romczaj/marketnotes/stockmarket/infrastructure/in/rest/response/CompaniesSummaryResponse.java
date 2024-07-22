package pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.response;


import java.util.List;

public record CompaniesSummaryResponse(
        List<CompanySummaryResponse> companies
) {


}

