package pl.romczaj.marketnotes.stockmarket.infrastructure.in.job;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "refresh-companies")
@Validated
record RefreshCompaniesProperties(
        CronExpression cron) {

    @ConstructorBinding
    public RefreshCompaniesProperties(@NotNull String cron) {
        this(CronExpression.parse(cron));
    }
}
