package pl.romczaj.marketnotes.useraccount.infrastructure.in.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReportScheduler {

    private final UserReportPort userReportPort;


    @Scheduled(cron = "0 * * * * *")
    public void prepareReports() {
        userReportPort.sendInvestReports();
    }
}
