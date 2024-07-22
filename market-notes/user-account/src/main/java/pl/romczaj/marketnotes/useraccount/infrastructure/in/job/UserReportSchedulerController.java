package pl.romczaj.marketnotes.useraccount.infrastructure.in.job;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-report-scheduler")
class UserReportSchedulerController {

    private final UserReportScheduler userReportScheduler;

    @GetMapping("/invoke")
    void invoke(){
        userReportScheduler.prepareReports();
    }
}
