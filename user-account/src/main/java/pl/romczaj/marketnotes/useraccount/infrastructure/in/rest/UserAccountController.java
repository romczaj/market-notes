package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.response.UserCompanyNotesResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-account")
public class UserAccountController {

    private final UserAccountRestManagement userAccountRestManagement;
    private final UserCompanyNotesReader userCompanyNotesReader;

    @PostMapping("/register")
    public void addAccount() {
        userAccountRestManagement.addAccount();
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
    @PostMapping("/company-comment")
    public void addCompanyNote(@RequestBody @Valid NoteCompanyComment noteCompanyComment) {
        userAccountRestManagement.noteCompanyComment(noteCompanyComment);
    }

    @GetMapping("/company-notes")
    public UserCompanyNotesResponse getCompanyNotes(
            @RequestParam("stockCompanyExternalId") StockCompanyExternalId stockCompanyExternalId) {
        return userCompanyNotesReader.getCompanyNotes(stockCompanyExternalId);
    }
}
