package pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider;


import pl.romczaj.marketnotes.common.clock.ApplicationClock;

import java.time.LocalDateTime;

class DataProviderTestConfiguration {

    private final static YahooConfiguration YAHOO_CONFIGURATION = new YahooConfiguration();
    static YahooApiProperties yahooApiProperties = new YahooApiProperties("https://query1.finance.yahoo.com");
    static YahooFeignInterface yahooFeignInterface = YAHOO_CONFIGURATION.githubFeignClient(yahooApiProperties);
    static FeignClientInvoker feignClientInvoker = new FeignClientInvoker(yahooFeignInterface);
    static DataProviderPort dataProviderPort = new YahooDataProvider(feignClientInvoker, ApplicationClock.defaultApplicationClock());

}
