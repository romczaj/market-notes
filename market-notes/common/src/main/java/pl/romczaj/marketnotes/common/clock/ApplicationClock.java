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

    public LocalDate localDate() {
        return LocalDate.now(clock);
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.now(clock);
    }

    public static ApplicationClock defaultApplicationClock() {
        return new ApplicationClock(Clock.systemDefaultZone());
    }

    public static ApplicationClock from(Instant instant) {
        return new ApplicationClock(
                Clock.fixed(instant, Clock.systemDefaultZone().getZone()));
    }

    public static ApplicationClock from(LocalDateTime localDateTime) {
        return new ApplicationClock(
                Clock.fixed(instantFrom(localDateTime), Clock.systemDefaultZone().getZone()));
    }

    public static Instant instantFrom(LocalDateTime localDateTime){
        return localDateTime.atZone(Clock.systemDefaultZone().getZone()).toInstant();
    }
}
