package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.domain.model.BalanceHistory;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.domain.model.RechargeHistory;
import pl.romczaj.marketnotes.useraccount.domain.model.BuySellHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserAccountRepository {

    default UserAccount getByExternalId(UserAccountExternalId userAccountExternalId) {
        return findByExternalId(userAccountExternalId)
                .orElseThrow(() -> new IllegalArgumentException("User account not found"));
    }
    UserAccount saveUserAccount(UserAccount userAccount);

    Optional<UserAccount> findByExternalId(UserAccountExternalId userAccountExternalId);

    void saveRechargeHistory(RechargeHistory rechargeHistory);

    void saveBuySellHistory(BuySellHistory buySellHistory);

    Optional<RechargeHistory> findRechargeHistory(Long userAccountId, LocalDate rechargeDate);

    Optional<BuySellHistory> findBuySellHistory(Long userAccountId, LocalDate operationDate,
                                                StockCompanyExternalId companyExternalId);
    Optional<BalanceHistory> findBalanceHistory(Long id, LocalDate localDate);

    BalanceHistory saveBalanceHistory(BalanceHistory balanceHistory);
    List<UserAccount> findAll();
    List<BalanceHistory> findBalanceHistoryByUserAccountId(Long userAccountId);
    List<RechargeHistory> findRechargeHistoryByUserAccountId(Long userAccountId);
    List<BuySellHistory> findBuySellHistoryByUserAccountId(Long userAccountId);

}
