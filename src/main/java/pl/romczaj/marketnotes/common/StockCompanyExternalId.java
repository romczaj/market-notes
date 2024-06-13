package pl.romczaj.marketnotes.common;


import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record StockCompanyExternalId(
        @NonNull String stockSymbol,
        @NonNull String stockMarketSymbol
) {

    @Override
    public String toString() {
        return stockSymbol + "_" + stockMarketSymbol;
    }

    public static StockCompanyExternalId fromString(String externalId) {
        Pattern pattern = Pattern.compile("^.+_.+$");
        Matcher matcher = pattern.matcher(externalId);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("externalId must be in the format 'stockSymbol_stockMarketSymbol'");
        }

        String[] parts = externalId.split("_");
        return new StockCompanyExternalId(parts[0], parts[1]);
    }
}
