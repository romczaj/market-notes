package pl.romczaj.marketnotes.useraccount.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;
import pl.romczaj.marketnotes.internalapi.AuthenticationRetriever;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi.StockCompanyResponse;
import pl.romczaj.marketnotes.useraccount.domain.command.CreateCompanyNoteCommand;
import pl.romczaj.marketnotes.useraccount.domain.model.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.UserAccountRestManagement;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.UserAccountRepository;

@Component
@RequiredArgsConstructor
public class UserAccountRestManagementProcess implements UserAccountRestManagement {

    private final StockMarketInternalApi stockMarketInternalApi;
    private final UserAccountRepository userAccountRepository;
    private final ApplicationClock applicationClock;
    private final AuthenticationRetriever authenticationRetriever;

    @Override
    public void addAccount(AddAccountRequest addAccountRequest) {
        if (userAccountRepository.existsByExternalId(addAccountRequest.userAccountExternalId())) {
          throw new RuntimeException("Account already exists");
        }

        UserAccount userAccount = UserAccount.createFrom(addAccountRequest);
        userAccountRepository.saveUserAccount(userAccount);
    }

    @Override
    public void noteAccountRecharge(NoteAccountRechargeRequest addAccountRequest) {
        UserAccount userAccount = retrieveUserAccount();
        RechargeHistory rechargeHistory = userAccountRepository
                .findRechargeHistory(userAccount.id(), addAccountRequest.rechargeDate())
                .map(u -> u.updateChargedMoney(addAccountRequest.rechargeAmount()))
                .orElse(RechargeHistory.create(userAccount.id(), addAccountRequest));

        userAccountRepository.saveRechargeHistory(rechargeHistory);
    }


    @Override
    public void noteBuySell(NoteBuySellRequest noteBuySellRequest) {
        stockMarketInternalApi.validateStockCompanyExists(noteBuySellRequest.stockCompanyExternalId());
        UserAccount userAccount = retrieveUserAccount();

        BuySellHistory buySellHistory = userAccountRepository.findBuySellHistory(
                        userAccount.id(), noteBuySellRequest.operationDate(), noteBuySellRequest.stockCompanyExternalId())
                .map(u -> u.updateOperationPrice(noteBuySellRequest.operationPrice()))
                .orElse(BuySellHistory.create(userAccount.id(), noteBuySellRequest));

        userAccountRepository.saveBuySellHistory(buySellHistory);
    }


    @Override
    public void noteBalance(NoteBalanceRequest noteBalanceRequest) {
        UserAccount userAccount = retrieveUserAccount();

        BalanceHistory balanceHistory = userAccountRepository
                .findBalanceHistory(userAccount.id(), noteBalanceRequest.accountBalanceDate())
                .map(ba -> ba.updateBalance(noteBalanceRequest.accountBalance()))
                .orElseGet(() -> BalanceHistory.create(userAccount.id(), noteBalanceRequest));

        userAccountRepository.saveBalanceHistory(balanceHistory);
    }

    @Override
    public void noteCompanyInvestGoal(NoteCompanyInvestGoalRequest noteCompanyInvestGoalRequest) {
        stockMarketInternalApi.validateStockCompanyExists(noteCompanyInvestGoalRequest.stockCompanyExternalId());
        UserAccount userAccount = retrieveUserAccount();

        CompanyInvestGoal companyInvestGoal = userAccountRepository
                .findCompanyInvestGoal(userAccount.id(), noteCompanyInvestGoalRequest.stockCompanyExternalId())
                .map(g -> g.updatePrices(noteCompanyInvestGoalRequest))
                .orElse(CompanyInvestGoal.create(userAccount.id(), noteCompanyInvestGoalRequest));

        userAccountRepository.saveCompanyInvestGoal(companyInvestGoal);

    }

    @Override
    public void noteCompanyComment(NoteCompanyComment noteCompanyComment) {
        StockCompanyResponse stockCompanyResponse = stockMarketInternalApi.getCompanyBySymbol(noteCompanyComment.stockCompanyExternalId());
        UserAccount userAccount = retrieveUserAccount();

        CompanyComment companyComment = CompanyComment.createFrom(
                new CreateCompanyNoteCommand(
                        userAccount.id(),
                        noteCompanyComment.stockCompanyExternalId(),
                        applicationClock.instant(),
                        stockCompanyResponse.actualPrice().amount(),
                        noteCompanyComment.noteContent()
                )
        );

        userAccountRepository.saveCompanyComment(companyComment);
    }

    private UserAccount retrieveUserAccount() {
        return userAccountRepository.getByExternalId(authenticationRetriever.userAccountExternalId());
    }
}
