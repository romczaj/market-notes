package pl.romczaj.marketnotes.useraccount.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.domain.model.BalanceHistory;
import pl.romczaj.marketnotes.useraccount.domain.model.UserAccount;
import pl.romczaj.marketnotes.useraccount.domain.model.RechargeHistory;
import pl.romczaj.marketnotes.useraccount.domain.model.BuySellHistory;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.UserAccountRestManagement;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.AddAccountRequest;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteBalanceRequest;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteAccountRechargeRequest;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.NoteBuySellRequest;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.respose.AddAccountResponse;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

@Component
@RequiredArgsConstructor
public class UserAccountRestManagementProcess implements UserAccountRestManagement {

    private final StockMarketInternalApi stockMarketInternalApi;
    private final UserAccountRepository userAccountRepository;

    @Override
    public AddAccountResponse addAccount(AddAccountRequest addAccountRequest) {

        UserAccount userAccount = UserAccount.createFrom(addAccountRequest);
        UserAccount saveUserAccount = userAccountRepository.saveUserAccount(userAccount);

        return new AddAccountResponse(
                saveUserAccount.username(),
                saveUserAccount.externalId()
        );
    }

    @Override
    public void noteAccountRecharge(NoteAccountRechargeRequest addAccountRequest) {
        UserAccount userAccount = userAccountRepository.getByExternalId(addAccountRequest.userAccountExternalId());

        RechargeHistory rechargeHistory = userAccountRepository
                .findRechargeHistory(userAccount.id(), addAccountRequest.rechargeDate())
                .map(u -> u.updateChargedMoney(addAccountRequest.rechargeAmount()))
                .orElse(RechargeHistory.create(userAccount.id(), addAccountRequest));

        userAccountRepository.saveRechargeHistory(rechargeHistory);
    }


    @Override
    public void noteBuySell(NoteBuySellRequest noteBuySellRequest) {
        stockMarketInternalApi.validateStockCompanyExists(noteBuySellRequest.stockCompanyExternalId());
        UserAccount userAccount = userAccountRepository.getByExternalId(noteBuySellRequest.userAccountExternalId());

        BuySellHistory buySellHistory = userAccountRepository.findBuySellHistory(
                        userAccount.id(), noteBuySellRequest.operationDate(), noteBuySellRequest.stockCompanyExternalId())
                .map(u -> u.updateOperationPrice(noteBuySellRequest.operationPrice()))
                .orElse(BuySellHistory.create(userAccount.id(), noteBuySellRequest));

        userAccountRepository.saveBuySellHistory(buySellHistory);
    }


    @Override
    public void noteBalance(NoteBalanceRequest noteBalanceRequest) {
        UserAccount userAccount = userAccountRepository.getByExternalId(noteBalanceRequest.userAccountExternalId());

        BalanceHistory balanceHistory = userAccountRepository
                .findBalanceHistory(userAccount.id(), noteBalanceRequest.accountBalanceDate())
                .map(ba -> ba.updateBalance(noteBalanceRequest.accountBalance()))
                .orElseGet(() -> BalanceHistory.create(userAccount.id(), noteBalanceRequest));

        userAccountRepository.saveBalanceHistory(balanceHistory);
    }
}
