package pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.YahooFeignInterface.HistoricDataFeignResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@RequiredArgsConstructor
@Component
class FeignClientInvoker {

    private final YahooFeignInterface yahooFeignInterface;

    List<HistoricData> getHistoricalData(GetHistoricalDataCommand getHistoricalDataCommand) {

        List<HistoricDataFeignResponse> response = yahooFeignInterface.getCompanyHistoryData(
                getHistoricalDataCommand.companyShortcut,
                getHistoricalDataCommand.dateFrom.toEpochSecond(LocalTime.of(0, 0), UTC),
                getHistoricalDataCommand.dateTo.toEpochSecond(LocalTime.of(23, 59), UTC),
                getHistoricalDataCommand.interval.getDataProviderClientValue());

        return response.stream()
                .filter(r -> !r.closePrice().equals("null"))
                .map(r -> new HistoricData(
                        r.date(),
                        new Money(
                                Double.parseDouble(r.closePrice()),
                                getHistoricalDataCommand.stockCompanyExternalId.stockMarketSymbol().getCurrency()
                        )))
                .toList();
    }

    record GetHistoricalDataCommand(
            StockCompanyExternalId stockCompanyExternalId,
            String companyShortcut,
            LocalDate dateFrom,
            LocalDate dateTo,
            DataProviderInterval interval) {
    }


}
