package pl.romczaj.marketnotes.useraccount.application.report.subtask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriodResult;
import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification.CommonNotification;

import java.util.List;
import java.util.stream.Stream;

import static pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod.DAILY;
import static pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod.TWO_WEEKS;

class CommonNotificationSubtaskTest {

    private final CommonNotificationSubtask commonNotificationSubtask = new CommonNotificationSubtask();

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(-5.0, -5.0, true, true),
                Arguments.of(null, 0.0, null, false)
        );
    }


    @ParameterizedTest
    @MethodSource("testArguments")
    void shouldCorrectPrepareNotification(Double dailyIncreasePercent, Double dailyIncreasePercentExpectResult,
                                          Boolean twoWeeksLowestPeak, Boolean twoWeeksLowestPeakExpectResult) {
        //given
        CalculationResult calculationResult = buildCalculationResult(dailyIncreasePercent, twoWeeksLowestPeak);
        //when
        CommonNotification commonNotification = commonNotificationSubtask.commonNotification(calculationResult);
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(dailyIncreasePercentExpectResult, commonNotification.dailyIncreasePercent()),
                () -> Assertions.assertEquals(twoWeeksLowestPeakExpectResult, commonNotification.archivedAtLeastTwoWeekBottom())
        );
    }


    private CalculationResult buildCalculationResult(Double dailyIncreasePercent, Boolean twoWeeksLowestPeak) {
        return new CalculationResult(1.0, 1.0,
                List.of(
                        new IncreasePeriodResult(
                                DAILY,
                                dailyIncreasePercent,
                                1.0,
                                1.0,
                                false,
                                false
                        ),
                        new IncreasePeriodResult(
                                TWO_WEEKS,
                                1.0,
                                1.0,
                                1.0,
                                true,
                                twoWeeksLowestPeak
                        )
                ));
    }
}