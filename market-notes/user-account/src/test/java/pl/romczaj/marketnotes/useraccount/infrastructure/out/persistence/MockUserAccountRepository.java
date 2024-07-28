package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.common.repository.InMemoryRepository;
import pl.romczaj.marketnotes.useraccount.domain.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MockUserAccountRepository implements UserAccountRepository, InMemoryRepository {

    private final Map<Long, BalanceHistory> BALANCE_HISTORY_DB = new HashMap<>();
    private final Map<Long, RechargeHistory> RECHARGE_HISTORY_DB = new HashMap<>();
    private final Map<Long, BuySellHistory> OPERATION_HISTORY_DB = new HashMap<>();
    private final Map<Long, UserAccount> USER_ACCOUNT_DB = new HashMap<>();
    private final Map<Long, CompanyInvestGoal> COMPANY_INVEST_GOAL_DB = new HashMap<>();
    private final Map<Long, InvestReport> INVEST_REPORT_ENTITY_GB = new HashMap<>();
    private final Map<Long, CompanyComment> COMPANY_NOTE_DB = new HashMap<>();

    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        return saveDomainModel(userAccount, USER_ACCOUNT_DB, WithIdDomainMapper::withId);
    }

    @Override
    public Optional<UserAccount> findByExternalId(UserAccountExternalId userAccountExternalId) {
        return USER_ACCOUNT_DB.values().stream()
                .filter(u -> u.externalId().equals(userAccountExternalId))
                .findFirst();
    }

    @Override
    public void saveRechargeHistory(RechargeHistory rechargeHistory) {
        saveDomainModel(rechargeHistory, RECHARGE_HISTORY_DB, WithIdDomainMapper::withId);
    }

    @Override
    public void saveBuySellHistory(BuySellHistory buySellHistory) {
        saveDomainModel(buySellHistory, OPERATION_HISTORY_DB, WithIdDomainMapper::withId);
    }

    @Override
    public CompanyInvestGoal saveCompanyInvestGoal(CompanyInvestGoal companyInvestGoal) {
        return saveDomainModel(companyInvestGoal, COMPANY_INVEST_GOAL_DB, WithIdDomainMapper::withId);
    }

    @Override
    public InvestReport saveInvestReport(InvestReport investReport) {
        return saveDomainModel(investReport, INVEST_REPORT_ENTITY_GB, WithIdDomainMapper::withId);
    }

    @Override
    public Optional<RechargeHistory> findRechargeHistory(Long userAccountId, LocalDate rechargeDate) {
        return RECHARGE_HISTORY_DB.values().stream()
                .filter(r -> r.userAccountId().equals(userAccountId) && r.rechargeDate().equals(rechargeDate))
                .findFirst();
    }

    @Override
    public Optional<BuySellHistory> findBuySellHistory(Long userAccountId, LocalDate operationDate, StockCompanyExternalId companyExternalId) {
        return OPERATION_HISTORY_DB.values().stream()
                .filter(o -> o.userAccountId().equals(userAccountId)
                             && o.operationDate().equals(operationDate)
                             && o.stockCompanyExternalId().equals(companyExternalId))
                .findFirst();
    }

    @Override
    public Optional<BalanceHistory> findBalanceHistory(Long id, LocalDate localDate) {
        return BALANCE_HISTORY_DB.values().stream()
                .filter(b -> b.id().equals(id) && b.balanceDate().equals(localDate))
                .findFirst();
    }

    @Override
    public BalanceHistory saveBalanceHistory(BalanceHistory balanceHistory) {
        return saveDomainModel(balanceHistory, BALANCE_HISTORY_DB, WithIdDomainMapper::withId);
    }

    @Override
    public List<UserAccount> findAll() {
        return USER_ACCOUNT_DB.values().stream()
                .toList();
    }

    @Override
    public List<BalanceHistory> findBalanceHistoryByUserAccountId(Long userAccountId) {
        return BALANCE_HISTORY_DB.values().stream()
                .filter(b -> b.userAccountId().equals(userAccountId))
                .toList();
    }

    @Override
    public List<RechargeHistory> findRechargeHistoryByUserAccountId(Long userAccountId) {
        return RECHARGE_HISTORY_DB.values().stream()
                .filter(r -> r.userAccountId().equals(userAccountId))
                .toList();
    }

    @Override
    public List<BuySellHistory> findBuySellHistoryByUserAccountId(Long userAccountId) {
        return OPERATION_HISTORY_DB.values().stream()
                .filter(o -> o.userAccountId().equals(userAccountId))
                .toList();
    }

    @Override
    public List<CompanyInvestGoal> findCompanyInvestGoalByUserAccountId(Long userAccountId) {
        return COMPANY_INVEST_GOAL_DB.values().stream()
                .filter(c -> c.userAccountId().equals(userAccountId))
                .toList();
    }

    @Override
    public List<InvestReport> findInvestReportByUserAccountId(Long userAccountId) {
        return INVEST_REPORT_ENTITY_GB.values().stream()
                .filter(i -> i.userAccountId().equals(userAccountId))
                .toList();
    }

    @Override
    public Optional<CompanyInvestGoal> findCompanyInvestGoal(Long userAccountId, StockCompanyExternalId stockCompanyExternalId) {
        return COMPANY_INVEST_GOAL_DB.values().stream()
                .filter(c -> c.userAccountId().equals(userAccountId) && c.stockCompanyExternalId().equals(stockCompanyExternalId))
                .findFirst();
    }

    @Override
    public void saveCompanyComment(CompanyComment companyComment) {
        saveDomainModel(companyComment, COMPANY_NOTE_DB, WithIdDomainMapper::withId);
    }

    @Override
    public List<CompanyComment> findCompanyCommentByUserAccountId(Long userAccountId) {
        return COMPANY_NOTE_DB.values().stream()
                .filter(c -> c.userAccountId().equals(userAccountId))
                .toList();
    }

}
