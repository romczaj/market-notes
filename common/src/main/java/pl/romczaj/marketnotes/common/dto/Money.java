package pl.romczaj.marketnotes.common.dto;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public record Money(
        @NotNull Double amount,
        @NotNull Currency currency



) {
    public enum Currency {
        PLN,
        EUR,
        USD
    }

    public boolean lessThan(Money money) {
        return this.amount < money.amount;
    }

    public static Money ofPln(Double amount) {
        return new Money(amount, Currency.PLN);
    }
}
