package pl.romczaj.marketnotes.useraccount.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteBalanceRequest;

import java.time.LocalDate;

public record BalanceHistory(
        Long id,
        Long userAccountId,
        @With Money balance,
        LocalDate balanceDate
) implements DomainModel {
    public static BalanceHistory create(Long userAccountId, NoteBalanceRequest request) {
        return new BalanceHistory(null, userAccountId, request.accountBalance(), request.accountBalanceDate());
    }

    public BalanceHistory updateBalance(Money balance) {
        return this.withBalance(balance);
    }
}
