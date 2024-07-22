package pl.romczaj.marketnotes.useraccount.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.dto.StockOperation;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.application.config.BaseApplicationTest;
import pl.romczaj.marketnotes.useraccount.domain.model.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;
import static pl.romczaj.marketnotes.useraccount.application.config.DefaultValues.*;

@Slf4j
class UserAccountRestManagementProcessTest extends BaseApplicationTest {

    @Test
    void shouldAddAccount() {
        //given
        AddAccountRequest addAccountRequest = new AddAccountRequest(
                DEFAULT_LOGGED_ACCOUNT_EXTERNAL_ID,
                DEFAULT_LOGGED_EMAIL,
                DEFAULT_LOGGED_NAME
        );

        // when
        userAccountRestManagement.addAccount(addAccountRequest);

        // then
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        assertAll(
                () -> assertEquals(1, userAccounts.size()),
                () -> assertTrue(userAccountRepository.existsByExternalId(DEFAULT_LOGGED_ACCOUNT_EXTERNAL_ID)),
                () -> assertEquals(DEFAULT_LOGGED_NAME, userAccounts.get(0).username()),
                () -> assertEquals(DEFAULT_LOGGED_EMAIL, userAccounts.get(0).email()));
    }

    @Test
    void shouldNoteBrokerAccountCharge() {
        // given
        UserAccount userAccount = createDefault();
        NoteAccountRechargeRequest noteAccountRechargeRequest = new NoteAccountRechargeRequest(
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
        UserAccount userAccount = createDefault();
        NoteBuySellRequest request = new NoteBuySellRequest(
                StockOperation.BUY,
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
        UserAccount userAccount = createDefault();
        NoteBalanceRequest request = new NoteBalanceRequest(
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
        UserAccount userAccount = createDefault();
        NoteCompanyInvestGoalRequest request = new NoteCompanyInvestGoalRequest(
                new StockCompanyExternalId("ASS", WSE),
                100.0,
                50.0,
                40.0,
                20.0
        );

        //when
        userAccountRestManagement.noteCompanyInvestGoal(request);

        //then
        List<CompanyInvestGoal> companyInvestGoals = userAccountRepository.findCompanyInvestGoalByUserAccountId(userAccount.id());
        CompanyInvestGoal companyInvestGoal = companyInvestGoals.get(0);
        assertAll(
                () -> assertEquals(1, companyInvestGoals.size()),
                () -> assertEquals(request.buyStopPrice(), companyInvestGoal.buyStopPrice()),
                () -> assertEquals(request.sellStopPrice(), companyInvestGoal.sellStopPrice()),
                () -> assertEquals(request.buyLimitPrice(), companyInvestGoal.buyLimitPrice()),
                () -> assertEquals(request.sellLimitPrice(), companyInvestGoal.sellLimitPrice())
        );
    }

    @Test
    void shouldAddCompanyNote(){
        //given
        UserAccount userAccount = createDefault();
        NoteCompanyComment noteCompanyComment = new NoteCompanyComment(
                new StockCompanyExternalId("ASS", WSE),
                "good company"
        );

        //when
        userAccountRestManagement.noteCompanyComment(noteCompanyComment);

        //then
        List<CompanyComment> companyComments = userAccountRepository.findCompanyCommentByUserAccountId(userAccount.id());
        assertAll(
                () -> assertEquals(1, companyComments.size()),
                () -> assertEquals(noteCompanyComment.noteContent(), companyComments.get(0).noteContent()),
                () -> assertEquals(noteCompanyComment.stockCompanyExternalId(), companyComments.get(0).stockCompanyExternalId())
        );
    }

    private UserAccount createDefault() {
        return userAccountRepository.saveUserAccount(
                new UserAccount(
                        null,
                        DEFAULT_LOGGED_ACCOUNT_EXTERNAL_ID,
                        DEFAULT_LOGGED_NAME,
                        DEFAULT_LOGGED_EMAIL)
        );
    }

}