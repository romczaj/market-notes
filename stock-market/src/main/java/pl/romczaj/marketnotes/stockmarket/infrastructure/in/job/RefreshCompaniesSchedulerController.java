package pl.romczaj.marketnotes.stockmarket.infrastructure.in.job;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refresh-companies-scheduler")
public class RefreshCompaniesSchedulerController {

    private final RefreshCompaniesScheduler refreshCompaniesScheduler;

    @GetMapping("/invoke")
    void invoke(){
        refreshCompaniesScheduler.refreshCompanies();
    }
}
