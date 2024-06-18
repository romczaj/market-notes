package pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.util.Comparator;
import java.util.List;


public interface DataProviderPort {

    GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand);

    record GetCompanyDataCommand(
            StockCompanyExternalId stockCompanyExternalId,
            String dataProviderSymbol,
            Integer fromDaysBefore,
            DataProviderInterval dataProviderInterval) {
    }

    record GetCompanyDataResult(String dataProviderSymbol, List<HistoricData> historicData) {
        public HistoricData getLatest() {
            return historicData.stream()
                    .max(Comparator.comparing(HistoricData::date))
                    .orElseThrow(() -> new IllegalArgumentException("No data found for company " + dataProviderSymbol));
        }
    }

    @Getter
    @RequiredArgsConstructor
    enum DataProviderInterval {
        DAILY("1d"),
        WEEKLY("1wk"),
        MONTHLY("1mo");

        private final String dataProviderClientValue;
    }


}
