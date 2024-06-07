package pl.romczaj.marketnotes.infrastructure.out.dataprovider;


import static pl.romczaj.marketnotes.common.ApplicationClockTestConfiguration.applicationClock;

public class DataProviderTestConfiguration {

    private final static YahooConfiguration YAHOO_CONFIGURATION = new YahooConfiguration();
    public static YahooApiProperties yahooApiProperties = new YahooApiProperties("https://query1.finance.yahoo.com");
    public static YahooFeignInterface yahooFeignInterface = YAHOO_CONFIGURATION.githubFeignClient(yahooApiProperties);
    public static DataProviderPort dataProviderPort = new YahooDataProvider(yahooFeignInterface, applicationClock);


}
