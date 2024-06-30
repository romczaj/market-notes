package pl.romczaj.marketnotes.useraccount.domain.command;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.time.Instant;

public record CreateCompanyNoteCommand(
        Long userAccountId,
        StockCompanyExternalId stockCompanyExternalId,
        Instant noteDate,
        Double companyPrice,
        String noteContent
) {
}
