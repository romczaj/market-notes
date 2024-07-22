package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest;

import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.*;

public interface UserAccountRestManagement {
    void addAccount(AddAccountRequest addAccountRequest);
    void noteAccountRecharge(NoteAccountRechargeRequest addAccountRequest);
    void noteBuySell(NoteBuySellRequest noteBuySellRequest);
    void noteBalance(NoteBalanceRequest noteBalanceRequest);
    void noteCompanyInvestGoal(NoteCompanyInvestGoalRequest noteCompanyInvestGoalRequest);
    void noteCompanyComment(NoteCompanyComment noteCompanyComment);

}
