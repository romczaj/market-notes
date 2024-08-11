package pl.romczaj.marketnotes.useraccount.application.report.subtask;


import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class FilterInvestNotificationSubtask {

    public List<CompanyUserNotification> filterSignificant(List<CompanyUserNotification> notifications) {
        return notifications
                .stream().filter(n -> filterCommon(n.commonNotification()) || filterPersonalized(n.personalizedNotification()))
                .toList();
    }

    private boolean filterCommon(CompanyUserNotification.CommonNotification commonNotification) {
        return commonNotification.archivedAtLeastTwoWeekBottom()
               || commonNotification.dailyIncreasePercent() <= -3.0;
    }

    private boolean filterPersonalized(CompanyUserNotification.PersonalizedNotification personalizedNotification) {
        return isNotEmpty(personalizedNotification.breakStockOperation())
               || isNotEmpty(personalizedNotification.underStockOperation());
    }
}
