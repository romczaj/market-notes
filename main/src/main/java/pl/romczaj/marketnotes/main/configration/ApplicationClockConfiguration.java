package pl.romczaj.marketnotes.main.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.romczaj.marketnotes.common.clock.ApplicationClock;

import java.time.Clock;

@Configuration
public class ApplicationClockConfiguration {

    @Bean
    public ApplicationClock applicationClock() {
        return new ApplicationClock(Clock.systemDefaultZone());
    }
}
