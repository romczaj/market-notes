package pl.romczaj.marketnotes.stockmarket.application.counter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.romczaj.marketnotes.common.dto.HistoricData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalDate.of;
import static pl.romczaj.marketnotes.common.dto.Money.ofPln;


@Slf4j
class StockCompanyCounterTest {

    private static final List<HistoricData> historicData = Arrays.asList(
            new HistoricData(of(2022, 1, 1), ofPln(100.0)),
            new HistoricData(of(2022, 1, 2), ofPln(200.0)),
            new HistoricData(of(2022, 1, 3), ofPln(150.0)),
            new HistoricData(of(2022, 1, 4), ofPln(200.0)),
            new HistoricData(of(2022, 1, 5), ofPln(250.0)),
            new HistoricData(of(2022, 1, 6), ofPln(250.0)),
            new HistoricData(of(2022, 1, 8), ofPln(250.0)),
            //   new HistoricData(of(2022, 1, 9), null),
            //   new HistoricData(of(2022, 1, 10), null),
            //   new HistoricData(of(2022, 1, 11), null),
            //   new HistoricData(of(2022, 1, 12), null),
            new HistoricData(of(2022, 1, 13), ofPln(250.0))
    );

    private final StockCompanyCounter stockCompanyCounter = new StockCompanyCounter(historicData);

    private static Stream<Arguments> countIncreaseArguments() {
        return Stream.of(
                Arguments.of(of(2022, 1, 1), of(2022, 1, 2), 100.0),
                Arguments.of(of(2022, 1, 4), of(2022, 1, 5), 25.0),
                Arguments.of(of(2022, 1, 2), of(2022, 1, 3), -25.0)
        );
    }

    @ParameterizedTest
    @MethodSource("countIncreaseArguments")
    void shouldCountIncrease(LocalDate baseDate, LocalDate today, Double result) {
        //when
        Double increase = stockCompanyCounter.countIncrease(baseDate, today);

        //then
        Assertions.assertEquals(result, increase);
    }

    private static Stream<Arguments> findHistoricDataByDateArguments() {
        return Stream.of(
                Arguments.of(of(2022, 1, 2), of(2022, 1, 2)),
                Arguments.of(of(2022, 1, 9), of(2022, 1, 8)),
                Arguments.of(of(2022, 1, 10), of(2022, 1, 8)),
                Arguments.of(of(2022, 1, 11), of(2022, 1, 13)),
                Arguments.of(of(2022, 1, 12), of(2022, 1, 13))
        );
    }


    @ParameterizedTest
    @MethodSource("findHistoricDataByDateArguments")
    void shouldFindHistoricDataByDate(LocalDate wantedDate, LocalDate resultDate) {
        //when
        HistoricData historicDataByDate = stockCompanyCounter.findHistoricDataByDate(wantedDate);

        //then
        Assertions.assertEquals(resultDate, historicDataByDate.date());
    }

}
