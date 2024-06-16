package pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.FeignClientInvoker.GetHistoricalDataCommand;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;



@RequiredArgsConstructor
@Slf4j
@Component
public class YahooDataProvider implements DataProviderPort {

    private final FeignClientInvoker feignClientInvoker;
    private final ApplicationClock applicationClock;

    @Override
    public GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand) {
        log.info("Command {}", getCompanyDataCommand);
        LocalDate dateTo = applicationClock.today();
        LocalDate fromDate = dateTo.minusDays(getCompanyDataCommand.fromDaysBefore());


        List<HistoricData> historicDataList = feignClientInvoker.getHistoricalData(
                    new GetHistoricalDataCommand(
                            getCompanyDataCommand.dataProviderSymbol(),
                            fromDate,
                            dateTo,
                            getCompanyDataCommand.dataProviderInterval()
                    ));

        return new GetCompanyDataResult(
                    getCompanyDataCommand.dataProviderSymbol(),
                historicDataList);
    }

    @Override
    public GetCompanyCurrentValueResult getCompanyCurrentValue(GetCompanyCurrentValueCommand getCompanyCurrentValueCommand) {

        List<HistoricData> historicDataList = feignClientInvoker.getHistoricalData(
                new GetHistoricalDataCommand(
                        getCompanyCurrentValueCommand.dataProviderSymbol(),
                        applicationClock.today().minusDays(6),
                        applicationClock.today(),
                        DataProviderInterval.DAILY
                ));

        HistoricData historicData = historicDataList
                .stream().max(Comparator.comparing(HistoricData::date))
                .orElseThrow(() -> new IllegalArgumentException("No data found for company " + getCompanyCurrentValueCommand.dataProviderSymbol()));

        return new GetCompanyCurrentValueResult(
                getCompanyCurrentValueCommand.dataProviderSymbol(),
                historicData);
    }
}
