package pl.romczaj.marketnotes.useraccount.infrastructure.in.job;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "user-report")
@Validated
record UserReportProperties(
        CronExpression cron) {

    @ConstructorBinding
    public UserReportProperties(@NotNull String cron) {
        this(CronExpression.parse(cron));
    }
}
