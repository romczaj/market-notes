package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.response;

import pl.romczaj.marketnotes.common.dto.Money.Currency;
import pl.romczaj.marketnotes.common.dto.StockOperation;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record UserCompanyNotesResponse(
        UserAccountExternalId userAccountExternalId,
        StockCompanyExternalId stockCompanyExternalId,
        InvestGoalResponse investGoalResponse,
        List<CommentResponse> comments,
        List<BuySellOperationResponse> buySellOperationResponses

) {
    public record InvestGoalResponse(
            StockCompanyExternalId stockCompanyExternalId,
            Double buyStopPrice,
            Double sellStopPrice,
            Double buyLimitPrice,
            Double sellLimitPrice
    ) {

        public static InvestGoalResponse empty(StockCompanyExternalId stockCompanyExternalId){
            return new InvestGoalResponse(stockCompanyExternalId, 0.0, 0.0, 0.0, 0.0);
        }
    }

    public record CommentResponse(
            Instant noteDate,
            Double companyPrice,
            String noteContent
    ) {
    }

    public record BuySellOperationResponse(
            StockOperation stockOperation,
            Double operationAmount,
            Currency operationCurrency,
            LocalDate operationDate
    ) {
    }
}
