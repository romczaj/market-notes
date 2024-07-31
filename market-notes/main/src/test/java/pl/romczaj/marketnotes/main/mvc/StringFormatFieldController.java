package pl.romczaj.marketnotes.main.mvc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;

@RestController
public class StringFormatFieldController {

    @GetMapping("/json-response")
    public JsonTestApiModel jsonResponse() {
        return new JsonTestApiModel(
                StockCompanyExternalId.fromString("RBW_WSE"),
                UserAccountExternalId.fromString("e3837b9a-1c3a-4a17-8e5d-189c81f8f59a"));
    }

    @PostMapping("/json-request")
    public JsonTestApiModel jsonRequest(@RequestBody @Valid JsonTestApiModel jsonTestApiModel) {
        return jsonTestApiModel;
    }

    public record JsonTestApiModel(
            @NotNull StockCompanyExternalId stockCompanyExternalId,
            @NotNull UserAccountExternalId userAccountExternalId
    ) {
    }

    @GetMapping("/request-param")
    public JsonTestApiModel mvnParam(@RequestParam StockCompanyExternalId stockCompanyExternalId,
                                     @RequestParam UserAccountExternalId userAccountExternalId){
        return new JsonTestApiModel(stockCompanyExternalId, userAccountExternalId);
    }
}