package pl.romczaj.marketnotes.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.romczaj.marketnotes.common.dto.Money.Currency;

import static pl.romczaj.marketnotes.common.dto.Country.POLAND;
import static pl.romczaj.marketnotes.common.dto.Country.USA;
import static pl.romczaj.marketnotes.common.dto.Money.Currency.PLN;
import static pl.romczaj.marketnotes.common.dto.Money.Currency.USD;

@RequiredArgsConstructor
@Getter
public enum StockMarketSymbol {
    WSE(PLN, POLAND),
    NYSE(USD, USA),
    NASDAQ(USD, USA);

    private final Currency currency;
    private final Country country;
}
