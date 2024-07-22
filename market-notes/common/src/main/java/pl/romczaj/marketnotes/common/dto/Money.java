package pl.romczaj.marketnotes.common.dto;

import jakarta.validation.constraints.NotNull;

public record Money(
        @NotNull Double amount,
        @NotNull Currency currency


) implements Comparable<Money> {

    @Override
    public int compareTo(Money o) {
        if (this.currency != o.currency) {
            throw new IllegalArgumentException("Cannot compare money in different currencies");
        }
        return this.amount.compareTo(o.amount);
    }

    public enum Currency {
        PLN,
        EUR,
        USD
    }

    public static Money ofPln(Double amount) {
        return new Money(amount, Currency.PLN);
    }
}
