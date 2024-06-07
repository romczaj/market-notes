package pl.romczaj.marketnotes.infrastructure.out.dataprovider;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.HistoricData;
import pl.romczaj.marketnotes.common.ApplicationClock;
import pl.romczaj.marketnotes.infrastructure.out.dataprovider.YahooFeignInterface.HistoricDataFeignResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@RequiredArgsConstructor
@Slf4j
@Component
public class YahooDataProvider implements DataProviderPort {

    private final YahooFeignInterface yahooFeignInterface;
    private final ApplicationClock applicationClock;

    @Override
    public GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand) {
        log.info("Command {}", getCompanyDataCommand);
        LocalDate dateTo = applicationClock.today();
        LocalDate fromDate = dateTo.minusDays(getCompanyDataCommand.fromDaysBefore());

        List<HistoricDataFeignResponse> response = yahooFeignInterface.getCompanyHistoryData(
                getCompanyDataCommand.companyShortcut(),
                fromDate.toEpochSecond(LocalTime.of(0, 0), UTC),
                applicationClock.instant().toEpochMilli(),
                getCompanyDataCommand.dataProviderClientValue()
        );
        log.info("Found {} records for {} company", response.size(), getCompanyDataCommand.companyShortcut());
        return new GetCompanyDataResult(
                getCompanyDataCommand.companyShortcut(),
                response.stream().map(r -> new HistoricData(r.date(), r.closePrice())).toList()
        );
    }

    @Override
    public GetCompanyCurrentValueResult getCompanyCurrentValue(GetCompanyCurrentValueCommand getCompanyCurrentValueCommand) {
        return null;
    }
}
