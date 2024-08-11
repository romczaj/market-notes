package pl.romczaj.marketnotes.useraccount.application.report.subtask;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi.StockCompanyResponse;
import pl.romczaj.marketnotes.useraccount.common.notification.CompanyUserNotification;
import pl.romczaj.marketnotes.useraccount.domain.model.CompanyInvestGoal;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanyUserNotificationFactory {

    private final CommonNotificationSubtask commonNotificationSubtask = new CommonNotificationSubtask();
    private final PersonalizedNotificationSubtask personalizedNotificationSubtask = new PersonalizedNotificationSubtask();

    private final UserAccountRepository userAccountRepository;
    private final StockMarketInternalApi stockMarketInternalApi;


    public List<CompanyUserNotification> prepare(UserAccount userAccount) {
        List<StockCompanyResponse> stockCompanyResponses = stockMarketInternalApi.findAll();
        Map<StockCompanyExternalId, CompanyInvestGoal> userInvestGoals = userAccountRepository
                .findGroupedCompanyInvestGoalByUserAccountId(userAccount.id());

        return stockCompanyResponses.stream()
                .map(c -> companyUserNotification(c, userInvestGoals.get(c.stockCompanyExternalId())))
                .toList();
    }

    private CompanyUserNotification companyUserNotification(
            StockCompanyResponse stockCompanyResponse,
            CompanyInvestGoal companyInvestGoal) {

        return new CompanyUserNotification(
                stockCompanyResponse.companyName(),
                stockCompanyResponse.stockCompanyExternalId(),
                commonNotificationSubtask.commonNotification(stockCompanyResponse.calculationResult()),
                Optional.ofNullable(companyInvestGoal)
                        .map(g -> personalizedNotificationSubtask.personalizedNotification(g, stockCompanyResponse.calculationResult()))
                        .orElse(new CompanyUserNotification.PersonalizedNotification(List.of(), List.of()))
        );
    }
}
