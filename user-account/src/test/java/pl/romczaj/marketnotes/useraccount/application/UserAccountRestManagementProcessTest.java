package pl.romczaj.marketnotes.useraccount.application;

import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.common.StockOperation;
import pl.romczaj.marketnotes.useraccount.domain.model.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

class UserAccountRestManagementProcessTest extends BaseApplicationTest {

    @Test
    void shouldAddAccount() {
        // given
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                "jankowalski",
                "jankowalski@market-notes.pl"
        );

        // when
        userAccountRestManagement.addAccount(addAccountRequest);

        // then
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        assertAll(
                () -> assertEquals(1, userAccounts.size()),
                () -> assertEquals(addAccountRequest.username(), userAccounts.get(0).username()),
                () -> assertEquals(addAccountRequest.email(), userAccounts.get(0).email()));
    }

    @Test
    void shouldNoteBrokerAccountCharge() {
        // given
        UserAccount userAccount = getDefault();
        NoteAccountRechargeRequest noteAccountRechargeRequest = new NoteAccountRechargeRequest(
                userAccount.externalId(),
                Money.ofPln(1000.0),
                LocalDate.now()
        );
        // when
        userAccountRestManagement.noteAccountRecharge(noteAccountRechargeRequest);

        // then
        List<RechargeHistory> rechargeHistories = userAccountRepository.findRechargeHistoryByUserAccountId(userAccount.id());
        assertAll(
                () -> assertEquals(1, rechargeHistories.size()),
                () -> assertEquals(noteAccountRechargeRequest.rechargeAmount(), rechargeHistories.get(0).chargedMoney()),
                () -> assertEquals(noteAccountRechargeRequest.rechargeDate(), rechargeHistories.get(0).rechargeDate())
        );
    }

    @Test
    void shouldNoteStockOperation() {
        //given
        UserAccount userAccount = getDefault();
        NoteBuySellRequest request = new NoteBuySellRequest(
                StockOperation.BUY,
                userAccount.externalId(),
                new StockCompanyExternalId("ELK", WSE),
                Money.ofPln(100.0),
                LocalDate.now()
        );

        //when
        userAccountRestManagement.noteBuySell(request);

        //then
        List<BuySellHistory> operationHistories = userAccountRepository.findBuySellHistoryByUserAccountId(userAccount.id());
        assertAll(
                () -> assertEquals(1, operationHistories.size()),
                () -> assertEquals(request.operationPrice(), operationHistories.get(0).operationPrice()),
                () -> assertEquals(request.operationDate(), operationHistories.get(0).operationDate())
        );
    }
    
    @Test
    void shouldNoteBrokerAccountInfo() {
        //given
        UserAccount userAccount = getDefault();
        NoteBalanceRequest request = new NoteBalanceRequest(
                userAccount.externalId(),
                LocalDate.now(),
                Money.ofPln(100.0)
        );

        //when
        userAccountRestManagement.noteBalance(request);

        //then
        List<BalanceHistory> balanceHistories = userAccountRepository.findBalanceHistoryByUserAccountId(userAccount.id());
        assertAll(
                () -> assertEquals(1, balanceHistories.size()),
                () -> assertEquals(request.accountBalance(), balanceHistories.get(0).balance()),
                () -> assertEquals(request.accountBalanceDate(), balanceHistories.get(0).balanceDate())
        );
    }

    @Test
    void shouldNoteCompanyInvestGoal() {
        //given
        UserAccount userAccount = getDefault();
        NoteCompanyInvestGoalRequest request = new NoteCompanyInvestGoalRequest(
                userAccount.externalId(),
                new StockCompanyExternalId("ASS", WSE),
                100.0,
                50.0
        );

        //when
        userAccountRestManagement.noteCompanyInvestGoal(request);

        //then
        List<CompanyInvestGoal> companyInvestGoals = userAccountRepository.findCompanyInvestGoalByUserAccountId(userAccount.id());
        assertAll(
                () -> assertEquals(1, companyInvestGoals.size()),
                () -> assertEquals(request.buyPrice(), companyInvestGoals.get(0).buyPrice()),
                () -> assertEquals(request.sellPrice(), companyInvestGoals.get(0).sellPrice())
        );
    }




    private UserAccount getDefault() {
        return userAccountRepository.saveUserAccount(
                new UserAccount(
                        null,
                        UserAccountExternalId.generate(),
                        "jankowalski",
                        "jankowalski@market-notes.pl")
        );
    }

}