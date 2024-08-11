package pl.romczaj.marketnotes.useraccount.common.price;


public record ArchivePriceCommand(
         Double buyStopPrice,
         Double sellStopPrice,
         Double buyLimitPrice,
         Double sellLimitPrice,
         Double yesterdayPrice,
         Double todayPrice
) {
}
