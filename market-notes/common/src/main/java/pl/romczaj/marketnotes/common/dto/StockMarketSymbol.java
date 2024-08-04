package pl.romczaj.marketnotes.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.romczaj.marketnotes.common.dto.Money.Currency;

import static pl.romczaj.marketnotes.common.dto.Money.Currency.PLN;
import static pl.romczaj.marketnotes.common.dto.Money.Currency.USD;

@RequiredArgsConstructor
@Getter
public enum StockMarketSymbol {
    WSE(PLN),
    NYSE(USD),
    NASDAQ(USD);

    private final Currency currency;
}
