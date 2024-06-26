package pl.romczaj.marketnotes.useraccount.application.task;

import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;

import java.util.List;

public class FilterInvestNotificationTask {

    public static List<CompanyUserNotification> filterSignificant(List<CompanyUserNotification> notifications) {
        return notifications
                .stream().filter(n ->
                        n.archivedSellPrice()
                        || n.archivedBuyPrice()
                        || n.archivedAtLeastTwoWeekBottom())
                .toList();
    }
}
