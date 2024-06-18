package pl.romczaj.marketnotes.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.romczaj.marketnotes.common.dto.Money.Currency;

import static pl.romczaj.marketnotes.common.dto.Money.Currency.PLN;

@RequiredArgsConstructor
@Getter
public enum StockMarketSymbol {
    WSE(PLN);

    private final Currency currency;
}
