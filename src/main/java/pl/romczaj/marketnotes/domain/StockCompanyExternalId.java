package pl.romczaj.marketnotes.domain;


import lombok.NonNull;

public record StockCompanyExternalId(
        @NonNull String stockSymbol,
        @NonNull String stockMarketSymbol
) {

    public String toDatabaseColumn() {
        return stockSymbol + "_" + stockMarketSymbol;
    }

    public static StockCompanyExternalId fromDatabaseColumn(String externalId) {
        String[] parts = externalId.split("_");
        return new StockCompanyExternalId(parts[0], parts[1]);
    }
}
