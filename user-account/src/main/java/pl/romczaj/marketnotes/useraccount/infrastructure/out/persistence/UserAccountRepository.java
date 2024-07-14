package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.domain.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface UserAccountRepository {

    default UserAccount getByExternalId(UserAccountExternalId userAccountExternalId) {
        return findByExternalId(userAccountExternalId)
                .orElseThrow(() -> new IllegalArgumentException("User account not found"));
    }

    default Map<StockCompanyExternalId, CompanyInvestGoal> findGroupedCompanyInvestGoalByUserAccountId(Long userAccountId) {
        return findCompanyInvestGoalByUserAccountId(userAccountId).stream()
                .collect(Collectors.toMap(CompanyInvestGoal::stockCompanyExternalId, Function.identity()));
    }

    default boolean existsByExternalId(UserAccountExternalId userAccountExternalId) {
        return findByExternalId(userAccountExternalId).isPresent();
    }

    UserAccount saveUserAccount(UserAccount userAccount);

    Optional<UserAccount> findByExternalId(UserAccountExternalId userAccountExternalId);

    void saveRechargeHistory(RechargeHistory rechargeHistory);

    void saveBuySellHistory(BuySellHistory buySellHistory);
    CompanyInvestGoal saveCompanyInvestGoal(CompanyInvestGoal companyInvestGoal);
    InvestReport saveInvestReport(InvestReport investReport);

    Optional<RechargeHistory> findRechargeHistory(Long userAccountId, LocalDate rechargeDate);

    Optional<BuySellHistory> findBuySellHistory(Long userAccountId, LocalDate operationDate,
                                                StockCompanyExternalId companyExternalId);
    Optional<BalanceHistory> findBalanceHistory(Long id, LocalDate localDate);

    BalanceHistory saveBalanceHistory(BalanceHistory balanceHistory);
    List<UserAccount> findAll();
    List<BalanceHistory> findBalanceHistoryByUserAccountId(Long userAccountId);
    List<RechargeHistory> findRechargeHistoryByUserAccountId(Long userAccountId);
    List<BuySellHistory> findBuySellHistoryByUserAccountId(Long userAccountId);
    List<CompanyInvestGoal> findCompanyInvestGoalByUserAccountId(Long userAccountId);
    List<InvestReport> findInvestReportByUserAccountId(Long userAccountId);
    Optional<CompanyInvestGoal> findCompanyInvestGoal(Long userAccountId, StockCompanyExternalId stockCompanyExternalId);
    void saveCompanyComment(CompanyComment companyComment);
    List<CompanyComment> findCompanyCommentByUserAccountId(Long userAccountId);
}
