package pl.romczaj.marketnotes.common.clock;


import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class ApplicationClock {

    private final Clock clock;

    public Instant instant() {
        return clock.instant();
    }

    public LocalDate today() {
        return LocalDate.now(clock);
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public static ApplicationClock fromLocalDateTime(LocalDateTime localDateTime) {
        return new ApplicationClock(
                Clock.fixed(
                        localDateTime.atZone(Clock.systemDefaultZone().getZone()).toInstant(),
                        Clock.systemDefaultZone().getZone()));
    }
}
