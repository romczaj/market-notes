package pl.romczaj.marketnotes.stockmarket.application.config;

import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort;

import java.time.LocalDate;
import java.util.List;

import static pl.romczaj.marketnotes.common.dto.Money.ofPln;

public class DefaultValues {

    public static final DataProviderPort DEFAULT_DATA_PROVIDER = new DataProviderPort() {
        @Override
        public GetCompanyDataResult getCompanyData(GetCompanyDataCommand getCompanyDataCommand) {
            return new GetCompanyDataResult(
                    getCompanyDataCommand.dataProviderSymbol(),
                    List.of(
                            new HistoricData(LocalDate.now(), ofPln(100.0))
                    )
            );
        }
    };
}
