package pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider;


import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.FeignClientInvoker.GetHistoricalDataCommand;

import java.time.LocalDate;
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
        GetHistoricalDataCommand getHistoricalDataCommand = buildGetHistoricalDataCommand(getCompanyDataCommand);
        List<HistoricData> historicDataList = feignClientInvoker.getHistoricalData(getHistoricalDataCommand);
        return new GetCompanyDataResult(getCompanyDataCommand.dataProviderSymbol(), historicDataList);
    }

    @Override
    public CompanyExistsResult companyExistsResult(CompanyExistsCommand companyExistsCommand) {
        log.info("Command {}", companyExistsCommand);

        GetHistoricalDataCommand getHistoricalDataCommand = buildGetHistoricalDataCommand(
                new GetCompanyDataCommand(
                        companyExistsCommand.stockCompanyExternalId(),
                        companyExistsCommand.dataProviderSymbol(),
                        7,
                        DataProviderInterval.DAILY
                ));

        try {
            feignClientInvoker.getHistoricalData(getHistoricalDataCommand);
            return new CompanyExistsResult(true);
        } catch (FeignException feignException) {
            return new CompanyExistsResult(false);
        }
    }

    private GetHistoricalDataCommand buildGetHistoricalDataCommand(GetCompanyDataCommand getCompanyDataCommand) {
        LocalDate dateTo = applicationClock.localDate();
        LocalDate fromDate = dateTo.minusDays(getCompanyDataCommand.fromDaysBefore());
        return new GetHistoricalDataCommand(
                getCompanyDataCommand.stockCompanyExternalId(),
                getCompanyDataCommand.dataProviderSymbol(),
                fromDate,
                dateTo,
                getCompanyDataCommand.dataProviderInterval()
        );
    }
}


