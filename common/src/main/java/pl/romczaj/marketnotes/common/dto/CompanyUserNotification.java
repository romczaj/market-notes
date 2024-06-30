package pl.romczaj.marketnotes.common.dto;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.util.List;

public record CompanyUserNotification(
        String companyName,
        StockCompanyExternalId stockCompanyExternalId,
        List<ArchivePrice> archivePrices,
        boolean archivedAtLeastTwoWeekBottom
) {
}
