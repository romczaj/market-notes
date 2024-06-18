package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.domain.model.BalanceHistory;
import pl.romczaj.marketnotes.useraccount.domain.model.RechargeHistory;
import pl.romczaj.marketnotes.useraccount.domain.model.BuySellHistory;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.BalanceHistoryEntity;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.RechargeHistoryEntity;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.BuySellHistoryEntity;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.UserAccountEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MockUserAccountRepository implements UserAccountRepository {

    private static final Map<Long, BalanceHistoryEntity> BALANCE_HISTORY_DB = new HashMap<>();
    private static final Map<Long, RechargeHistoryEntity> RECHARGE_HISTORY_DB = new HashMap<>();
    private static final Map<Long, BuySellHistoryEntity> OPERATION_HISTORY_DB = new HashMap<>();
    private static final Map<Long, UserAccountEntity> USER_ACCOUNT_DB = new HashMap<>();
    private Long idCounter = 0L;

    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        UserAccountEntity userAccountEntity = UserAccountEntity.fromDomain(userAccount);
        if (userAccount.id() == null) {
            userAccountEntity.setId(++idCounter);
        }
        USER_ACCOUNT_DB.put(userAccountEntity.getId(), userAccountEntity);
        return userAccountEntity.toDomain();
    }

    @Override
    public Optional<UserAccount> findByExternalId(UserAccountExternalId userAccountExternalId) {
        return USER_ACCOUNT_DB.values().stream()
                .filter(u -> u.getExternalId().equals(userAccountExternalId))
                .map(UserAccountEntity::toDomain)
                .findFirst();
    }

    @Override
    public void saveRechargeHistory(RechargeHistory rechargeHistory) {
        RechargeHistoryEntity rechargeHistoryEntity = RechargeHistoryEntity.fromDomain(rechargeHistory);
        if (rechargeHistory.id() == null) {
            rechargeHistoryEntity.setId(++idCounter);
        }
        RECHARGE_HISTORY_DB.put(rechargeHistoryEntity.getId(), rechargeHistoryEntity);
    }

    @Override
    public void saveBuySellHistory(BuySellHistory buySellHistory) {
        BuySellHistoryEntity buySellHistoryEntity = BuySellHistoryEntity.fromDomain(buySellHistory);
        if (buySellHistory.id() == null) {
            buySellHistoryEntity.setId(++idCounter);
        }
        OPERATION_HISTORY_DB.put(buySellHistoryEntity.getId(), buySellHistoryEntity);
    }

    @Override
    public Optional<RechargeHistory> findRechargeHistory(Long userAccountId, LocalDate rechargeDate) {
        return RECHARGE_HISTORY_DB.values().stream()
                .filter(r -> r.getUserAccountId().equals(userAccountId) && r.getRechargeDate().equals(rechargeDate))
                .map(RechargeHistoryEntity::toDomain)
                .findFirst();
    }

    @Override
    public Optional<BuySellHistory> findBuySellHistory(Long userAccountId, LocalDate operationDate, StockCompanyExternalId companyExternalId) {
        return OPERATION_HISTORY_DB.values().stream()
                .filter(o -> o.getUserAccountId().equals(userAccountId)
                             && o.getOperationDate().equals(operationDate)
                             && o.getStockCompanyExternalId().equals(companyExternalId))
                .map(BuySellHistoryEntity::toDomain)
                .findFirst();
    }

    @Override
    public Optional<BalanceHistory> findBalanceHistory(Long id, LocalDate localDate) {
        return BALANCE_HISTORY_DB.values().stream()
                .filter(b -> b.getId().equals(id) && b.getBalanceDate().equals(localDate))
                .map(BalanceHistoryEntity::toDomain)
                .findFirst();
    }

    @Override
    public BalanceHistory saveBalanceHistory(BalanceHistory balanceHistory) {
        BalanceHistoryEntity balanceHistoryEntity = BalanceHistoryEntity.fromDomain(balanceHistory);
        if (balanceHistory.id() == null) {
            balanceHistoryEntity.setId(++idCounter);
        }
        BALANCE_HISTORY_DB.put(balanceHistoryEntity.getId(), balanceHistoryEntity);
        return balanceHistoryEntity.toDomain();
    }

    @Override
    public List<UserAccount> findAll() {
        return USER_ACCOUNT_DB.values().stream()
                .map(UserAccountEntity::toDomain)
                .toList();
    }

    @Override
    public List<BalanceHistory> findBalanceHistoryByUserAccountId(Long userAccountId) {
        return BALANCE_HISTORY_DB.values().stream()
                .filter(b -> b.getUserAccountId().equals(userAccountId))
                .map(BalanceHistoryEntity::toDomain)
                .toList();
    }

    @Override
    public List<RechargeHistory> findRechargeHistoryByUserAccountId(Long userAccountId) {
        return RECHARGE_HISTORY_DB.values().stream()
                .filter(r -> r.getUserAccountId().equals(userAccountId))
                .map(RechargeHistoryEntity::toDomain)
                .toList();
    }

    @Override
    public List<BuySellHistory> findBuySellHistoryByUserAccountId(Long userAccountId) {
        return OPERATION_HISTORY_DB.values().stream()
                .filter(o -> o.getUserAccountId().equals(userAccountId))
                .map(BuySellHistoryEntity::toDomain)
                .toList();
    }

}
