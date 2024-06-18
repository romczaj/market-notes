package pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.HistoricData;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.util.Objects;

import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;
import static pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;


@Slf4j
class YahooDataProviderTest {

    private final DataProviderPort dataProviderPort = DataProviderTestConfiguration.dataProviderPort;

    @Test
    void shouldReturnHistoricDataForCompany() {
        //given
        String companyName = "ELT.WA";
        //when
        DataProviderPort.GetCompanyDataResult getCompanyDataResult = dataProviderPort.getCompanyData(
                new DataProviderPort.GetCompanyDataCommand(
                        new StockCompanyExternalId("ELT", WSE),
                        companyName,
                        1000,
                        DAILY));
        //then

        Assertions.assertAll(
                () -> Assertions.assertFalse(getCompanyDataResult.historicData().isEmpty()),
                () -> Assertions.assertTrue(getCompanyDataResult.historicData().stream().map(HistoricData::closePrice).noneMatch(Objects::isNull)),
                () -> Assertions.assertTrue(getCompanyDataResult.historicData().stream().map(HistoricData::date).noneMatch(Objects::isNull))
        );
    }
}