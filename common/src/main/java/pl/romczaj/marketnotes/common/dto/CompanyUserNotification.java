package pl.romczaj.marketnotes.common.dto;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

public record CompanyUserNotification(
        StockCompanyExternalId stockCompanyExternalId,
        boolean archivedBuyPrice,
        boolean archivedSellPrice,
        boolean archivedAtLeastTwoWeekBottom

) {
}
