package pl.romczaj.marketnotes.useraccount.common.notification;


import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.common.price.StockOperation;

import java.util.List;

public record CompanyUserNotification(
        String companyName,
        StockCompanyExternalId stockCompanyExternalId,
        CommonNotification commonNotification,
        PersonalizedNotification personalizedNotification
) {

    public record CommonNotification(
            Double dailyIncreasePercent,
            Boolean archivedAtLeastTwoWeekBottom
    ) {
    }

    public record PersonalizedNotification(
            List<StockOperation> breakStockOperation,
            List<StockOperation> underStockOperation
    ) {
    }
}
