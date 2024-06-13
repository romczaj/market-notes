package pl.romczaj.marketnotes.infrastructure.out.dataprovider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.romczaj.marketnotes.common.HistoricData;

import java.util.List;


public interface DataProviderPort {

    GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand);

    GetCompanyCurrentValueResult getCompanyCurrentValue(GetCompanyCurrentValueCommand getCompanyCurrentValueCommand);

    record GetCompanyDataCommand(
            String dataProviderSymbol,
            Integer fromDaysBefore,
            DataProviderInterval dataProviderInterval) {
    }

    record GetCompanyDataResult(String companyName, List<HistoricData> historicData) {
    }

    @Getter
    @RequiredArgsConstructor
    enum DataProviderInterval {
        DAILY("1d"),
        WEEKLY("1wk"),
        MONTHLY("1mo");

        private final String dataProviderClientValue;
    }

    record GetCompanyCurrentValueCommand(String dataProviderSymbol) {
    }

    record GetCompanyCurrentValueResult(String dataProviderSymbol, HistoricData lastHistoricData) {
    }

}
