package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest;

import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.respose.AddAccountResponse;

public interface UserAccountRestManagement {
    AddAccountResponse addAccount(AddAccountRequest addAccountRequest);
    void noteAccountRecharge(NoteAccountRechargeRequest addAccountRequest);
    void noteBuySell(NoteBuySellRequest noteBuySellRequest);
    void noteBalance(NoteBalanceRequest noteBalanceRequest);
    void noteCompanyInvestGoal(NoteCompanyInvestGoalRequest noteCompanyInvestGoalRequest);

}
