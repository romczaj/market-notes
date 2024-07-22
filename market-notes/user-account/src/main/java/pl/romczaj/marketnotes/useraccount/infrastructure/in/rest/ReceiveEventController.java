package pl.romczaj.marketnotes.useraccount.infrastructure.in.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.request.AddAccountRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class ReceiveEventController {

    private final UserAccountRestManagement userAccountRestManagement;

    @PostMapping("/add-account")
    public void registerAccount(@RequestBody @Valid AddAccountRequest addAccountRequest){
        userAccountRestManagement.addAccount(addAccountRequest);
    }
}
