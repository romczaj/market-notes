package pl.romczaj.marketnotes.infrastructure.in.rest.respose;

import java.util.List;

public record FullCompanyResponse(
        StockCompanyBaseInfoResponse baseInfo,
        List<CompanyNoteResponse> notes,
        AnalyzeResponse analyze
) {

}
