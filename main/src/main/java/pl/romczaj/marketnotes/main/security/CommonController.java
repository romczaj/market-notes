package pl.romczaj.marketnotes.main.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/common/test")
    public String getCompany() {
        return "companyReader.getCompany(companyId)";
    }
}
