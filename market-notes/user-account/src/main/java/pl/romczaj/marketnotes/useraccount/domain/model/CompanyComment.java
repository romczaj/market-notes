package pl.romczaj.marketnotes.useraccount.domain.model;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.useraccount.domain.command.CreateCompanyNoteCommand;

import java.time.Instant;

public record CompanyComment(
        Long id,
        Long userAccountId,
        StockCompanyExternalId stockCompanyExternalId,
        Instant noteDate,
        Double companyPrice,
        String noteContent
) implements DomainModel {
    public static CompanyComment createFrom(CreateCompanyNoteCommand command) {
        return new CompanyComment(
                null,
                command.userAccountId(),
                command.stockCompanyExternalId(),
                command.noteDate(),
                command.companyPrice(),
                command.noteContent()
        );
    }

}