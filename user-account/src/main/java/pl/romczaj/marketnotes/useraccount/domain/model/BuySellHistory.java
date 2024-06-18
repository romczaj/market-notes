package pl.romczaj.marketnotes.useraccount.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.common.StockOperation;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteBuySellRequest;

import java.time.LocalDate;

public record BuySellHistory(
        Long id,
        Long userAccountId,
        StockCompanyExternalId stockCompanyExternalId,
        StockOperation stockOperation,
        @With Money operationPrice,
        LocalDate operationDate

) {
    public static BuySellHistory create(Long userAccountId, NoteBuySellRequest noteBuySellRequest) {
        return new BuySellHistory(
                null,
                userAccountId,
                noteBuySellRequest.stockCompanyExternalId(),
                noteBuySellRequest.stockOperation(),
                noteBuySellRequest.operationPrice(),
                noteBuySellRequest.operationDate()
        );
    }

    public BuySellHistory updateOperationPrice(Money operationPrice) {
        return this.withOperationPrice(operationPrice);
    }
}
