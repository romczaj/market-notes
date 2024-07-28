package pl.romczaj.marketnotes.useraccount.domain.model;

import lombok.With;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.domain.DomainModel;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteAccountRechargeRequest;

import java.time.LocalDate;

public record RechargeHistory(
        Long id,
        Long userAccountId,
        @With Money chargedMoney,
        LocalDate rechargeDate
) implements DomainModel {
    public static RechargeHistory create(Long userAccountId, NoteAccountRechargeRequest noteAccountRechargeRequest) {
        return new RechargeHistory(
                null,
                userAccountId,
                noteAccountRechargeRequest.rechargeAmount(),
                noteAccountRechargeRequest.rechargeDate()
        );
    }

    public RechargeHistory updateChargedMoney(Money chargedMoney) {
        return this.withChargedMoney(chargedMoney);
    }
}
