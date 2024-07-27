package pl.romczaj.marketnotes.useraccount.application.task;

import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.ArchivePrice;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class FilterInvestNotificationSubtaskTest {

    private static final List<CompanyUserNotification> NOTIFICATIONS = List.of(
            new CompanyUserNotification("AAA company", new StockCompanyExternalId("AAA", WSE), List.of(ArchivePrice.SELL_LIMIT), true),
            new CompanyUserNotification("BBB company", new StockCompanyExternalId("BBB", WSE), List.of(ArchivePrice.BUY_LIMIT), false),
            new CompanyUserNotification("CCC company", new StockCompanyExternalId("CCC", WSE), List.of(), true),
            new CompanyUserNotification("DDD company", new StockCompanyExternalId("DDD", WSE), List.of(), false)
    );

    @Test
    void shouldFilterSignificant() {

        // when
        List<CompanyUserNotification> result = FilterInvestNotificationSubtask.filterSignificant(NOTIFICATIONS);

        // then
        assertEquals(3, result.size());
    }

}