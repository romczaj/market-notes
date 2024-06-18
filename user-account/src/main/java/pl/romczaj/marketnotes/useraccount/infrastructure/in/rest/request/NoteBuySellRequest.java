package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request;

import jakarta.validation.constraints.NotNull;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.common.StockOperation;

import java.time.LocalDate;

public record NoteBuySellRequest(
        @NotNull StockOperation stockOperation,
        @NotNull UserAccountExternalId userAccountExternalId,
        @NotNull StockCompanyExternalId stockCompanyExternalId,
        @NotNull Money operationPrice,
        @NotNull LocalDate operationDate
) implements UserAccountOperationRequest {

}
