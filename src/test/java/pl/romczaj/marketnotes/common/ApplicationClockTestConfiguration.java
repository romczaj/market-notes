package pl.romczaj.marketnotes.common;

import pl.romczaj.marketnotes.common.ApplicationClock;
import pl.romczaj.marketnotes.common.ApplicationClockConfiguration;

public class ApplicationClockTestConfiguration {

    private static final ApplicationClockConfiguration applicationClockConfiguration = new ApplicationClockConfiguration();
    public static ApplicationClock applicationClock = applicationClockConfiguration.applicationClock();
}
