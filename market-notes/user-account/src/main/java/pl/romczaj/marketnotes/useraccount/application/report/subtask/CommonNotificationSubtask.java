package pl.romczaj.marketnotes.useraccount.application.report.subtask;

import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriodResult;
import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;

import static pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod.DAILY;
import static pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod.TWO_WEEKS;

public class CommonNotificationSubtask {

    CompanyUserNotification.CommonNotification commonNotification(CalculationResult calculationResult) {

        Double dailyIncreasePercent = calculationResult.increasePeriodResults().stream()
                .filter(increasePeriodResult -> DAILY == increasePeriodResult.increasePeriod())
                .findFirst()
                .map(IncreasePeriodResult::increasePercent).orElse(0.0);

        Boolean archivedAtLeastTwoWeekBottom = calculationResult.increasePeriodResults().stream()
                .filter(increasePeriodResult -> TWO_WEEKS == increasePeriodResult.increasePeriod())
                .findFirst()
                .map(IncreasePeriodResult::scoreLowestPeak).orElse(false);

        return new CompanyUserNotification.CommonNotification(
                dailyIncreasePercent,
                archivedAtLeastTwoWeekBottom
        );
    }
}
