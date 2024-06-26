package pl.romczaj.marketnotes.useraccount.application.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriodResult;
import pl.romczaj.marketnotes.common.dto.CompanyUserNotification;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi.StockCompanyResponse;
import pl.romczaj.marketnotes.useraccount.domain.model.CompanyInvestGoal;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.romczaj.marketnotes.common.dto.CalculationResult.IncreasePeriod.TWO_WEEKS;

@Component
@RequiredArgsConstructor
public class PrepareInvestReportDataTask {

    private final UserAccountRepository userAccountRepository;
    private final StockMarketInternalApi stockMarketInternalApi;


    public List<CompanyUserNotification> prepare(UserAccount userAccount) {
        List<StockCompanyExternalId> stockCompanyExternalIds = stockMarketInternalApi.findAll();
        Map<StockCompanyExternalId, CompanyInvestGoal> userInvestGoals = userAccountRepository.findGroupedCompanyInvestGoalByUserAccountId(userAccount.id());

        return stockCompanyExternalIds.stream()
                .map(a -> {
                    StockCompanyResponse stockCompanyResponse = stockMarketInternalApi.getCompanyBySymbol(a);
                    return processSingleCompany(stockCompanyResponse, userInvestGoals.get(a));
                }).toList();
    }

    private CompanyUserNotification processSingleCompany(StockCompanyResponse stockCompanyResponse, CompanyInvestGoal companyInvestGoal) {
        CalculationResult calculationResult = stockCompanyResponse.companyCounts();
        Optional<CompanyInvestGoal> maybeCompanyInvestGoal = Optional.ofNullable(companyInvestGoal);
        return new CompanyUserNotification(
                stockCompanyResponse.stockCompanyExternalId(),
                maybeCompanyInvestGoal.map(g -> g.archivedBuyPrice(calculationResult.yesterdayPrice(), calculationResult.todayPrice())).orElse(false),
                maybeCompanyInvestGoal.map(g -> g.archivedSellPrice(calculationResult.yesterdayPrice(), calculationResult.todayPrice())).orElse(false),
                twoWeeksIncreasePeriod(calculationResult)
        );
    }

    private boolean twoWeeksIncreasePeriod(CalculationResult calculationResult) {
        return calculationResult.increasePeriodResult()
                .stream().filter(c -> c.increasePeriod() == TWO_WEEKS)
                .findFirst()
                .map(IncreasePeriodResult::scoreLowestPeak)
                .orElse(false);
    }


}
