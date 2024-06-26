package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.respose.AddAccountResponse;

@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountRestManagement userAccountRestManagement;

    @PostMapping("/add-account")
    public AddAccountResponse addAccount(@RequestBody @Valid AddAccountRequest addAccountRequest) {
      return userAccountRestManagement.addAccount(addAccountRequest);
    }

    @PostMapping("/note-account-recharge")
    public void noteAccountRecharge(@RequestBody @Valid NoteAccountRechargeRequest noteAccountRechargeRequest) {
        userAccountRestManagement.noteAccountRecharge(noteAccountRechargeRequest);
    }

    @PostMapping("/note-buy-sell")
    public void noteBuySell(@RequestBody @Valid NoteBuySellRequest noteBuySellRequest) {
        userAccountRestManagement.noteBuySell(noteBuySellRequest);
    }

    @PostMapping("/note-balance")
    public void noteBalance(@RequestBody @Valid NoteBalanceRequest noteBalanceRequest) {
        userAccountRestManagement.noteBalance(noteBalanceRequest);
    }

    @PostMapping("/company-invest-goal")
    public void companyInvestGoal(@RequestBody @Valid NoteCompanyInvestGoalRequest noteCompanyInvestGoalRequest) {
        userAccountRestManagement.noteCompanyInvestGoal(noteCompanyInvestGoalRequest);
    }

}
