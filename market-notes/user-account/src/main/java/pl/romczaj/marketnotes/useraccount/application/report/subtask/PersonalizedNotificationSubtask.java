package pl.romczaj.marketnotes.useraccount.application.report.subtask;

import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification.PersonalizedNotification;
import pl.romczaj.marketnotes.useraccount.common.price.StockOperation;
import pl.romczaj.marketnotes.useraccount.common.price.ArchivePriceCommand;
import pl.romczaj.marketnotes.useraccount.domain.model.CompanyInvestGoal;


import java.util.List;

public class PersonalizedNotificationSubtask {

    PersonalizedNotification personalizedNotification(
            CompanyInvestGoal companyInvestGoal,
            CalculationResult calculationResult) {

        ArchivePriceCommand archivePriceCommand = companyInvestGoal.toArchivePriceCommand(
                calculationResult.yesterdayPrice(), calculationResult.todayPrice());
        List<StockOperation> breakStockOperation = StockOperation.listBreak(archivePriceCommand);
        List<StockOperation> listStockOperation = StockOperation.listUnder(archivePriceCommand);

        return new PersonalizedNotification(
                breakStockOperation,
                listStockOperation
        );
    }
}
