package pl.romczaj.marketnotes.infrastructure.out.dataprovider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.romczaj.marketnotes.common.HistoricData;

import java.util.List;
import java.util.Optional;

import static pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;


public interface DataProviderPort {

    GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand);
    GetCompanyCurrentValueResult getCompanyCurrentValue(GetCompanyCurrentValueCommand getCompanyCurrentValueCommand);

    record GetCompanyDataCommand(
            String companyShortcut,
            Integer fromDaysBefore,
            DataProviderInterval dataProviderInterval) {

        public String dataProviderClientValue() {
            return Optional.ofNullable(dataProviderInterval)
                    .map(DataProviderInterval::getDataProviderClientValue)
                    .orElse(DAILY.getDataProviderClientValue());
        }
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

    record GetCompanyCurrentValueResult(Double currentValue) {
    }

}
