package pl.romczaj.marketnotes.useraccount.infrastructure.in.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReportScheduler {

    private final UserReportSchedulePort userReportSchedulePort;


    @Scheduled(cron = "${user-report.cron}")
    public void prepareReports() {
        userReportSchedulePort.prepareAndSend();
    }
}
