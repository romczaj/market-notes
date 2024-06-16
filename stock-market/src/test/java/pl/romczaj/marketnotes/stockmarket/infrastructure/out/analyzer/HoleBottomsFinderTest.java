package pl.romczaj.marketnotes.stockmarket.infrastructure.out.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.HistoricData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.of;


@Slf4j
class HoleBottomsFinderTest {

    private final HoleBottomsFinder holeBottomsFinder = new HoleBottomsFinder();
    private final List<HistoricData> historicData = Arrays.asList(
            new HistoricData(of(2022, 1, 1), 100.0),
            new HistoricData(of(2022, 1, 2), 200.0),
            new HistoricData(of(2022, 1, 3), 150.0),
            new HistoricData(of(2022, 1, 4), 200.0),
            new HistoricData(of(2022, 1, 5), 250.0)
    );


    @Test
    void printHoles() {
        //when
        List<HistoricData> foundHoleBottoms = holeBottomsFinder.findHoleBottoms(historicData);

        //then
        foundHoleBottoms.forEach(historicData1 -> log.info("{}", historicData1));
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, foundHoleBottoms.size()),
                () -> Assertions.assertTrue(foundHoleBottoms.contains(historicData.get(2)))
        );
    }

    @Test
    void shouldCountIncrease() {
        //when
        Double increase = holeBottomsFinder.countIncrease(historicData.get(0), historicData.get(historicData.size() - 1));

        //then
        Assertions.assertEquals(150.0, increase);
    }

    @Test
    void shouldCountIncreaseNegative() {
        //when
        Double increase = holeBottomsFinder.countIncrease(historicData.get(1), historicData.get(2));

        //then
        Assertions.assertEquals(-25, increase);
    }

    @Test
    void shouldFindHistoricDataByDate() {
        //given
        Map<LocalDate, HistoricData> historicDataMap = new HashMap<>();
        addValueInMap(historicDataMap, of(2022, 1, 1), 100.0);
        addValueInMap(historicDataMap, of(2022, 1, 3), 150.0);
        addValueInMap(historicDataMap, of(2022, 1, 5), 200.0);

        //when
        HistoricData middleDate = holeBottomsFinder.findHistoricDataByDate(historicDataMap, of(2022, 1, 4));
        HistoricData lastDate = holeBottomsFinder.findHistoricDataByDate(historicDataMap, of(2022, 1, 1));
        HistoricData notExistDate = holeBottomsFinder.findHistoricDataByDate(historicDataMap, of(2021, 12, 31));

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(new HistoricData(of(2022, 1, 3), 150.0), middleDate),
                () -> Assertions.assertEquals(new HistoricData(of(2022, 1, 1), 100.0), lastDate),
                () -> Assertions.assertNull(notExistDate)
        );
    }

    private void addValueInMap(Map<LocalDate, HistoricData> map, LocalDate localDate, double value) {
        map.put(localDate, new HistoricData(localDate, value));
    }
}