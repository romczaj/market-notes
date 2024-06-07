package pl.romczaj.marketnotes.infrastructure.out.dataprovider;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static pl.romczaj.marketnotes.infrastructure.out.dataprovider.DataProviderPort.DataProviderInterval.DAILY;

@Slf4j
class YahooDataProviderTest {

    private final DataProviderPort dataProviderPort = DataProviderTestConfiguration.dataProviderPort;

    @Test
    void shouldReturnHistoricDataForCompany() {
        //given
        String companyName = "ELT.WA";
        //when
        DataProviderPort.GetCompanyDataResult result = dataProviderPort.getCompanyData(
                new DataProviderPort.GetCompanyDataCommand(
                        companyName,
                        1000,
                        DAILY));
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(companyName, result.companyName()),
                () -> Assertions.assertFalse(result.historicData().isEmpty())
        );
    }


}