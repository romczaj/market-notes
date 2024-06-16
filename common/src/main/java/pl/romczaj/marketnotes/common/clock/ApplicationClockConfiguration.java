package pl.romczaj.marketnotes.common.clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationClockConfiguration {

    @Bean
    public ApplicationClock applicationClock() {
        return new ApplicationClock(Clock.systemDefaultZone());
    }
}
