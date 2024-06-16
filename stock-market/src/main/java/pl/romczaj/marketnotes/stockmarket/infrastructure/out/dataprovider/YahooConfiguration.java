package pl.romczaj.marketnotes.stockmarket.infrastructure.out.dataprovider;

import feign.Feign;
import feign.Logger;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class YahooConfiguration {

    @Bean
    public YahooFeignInterface githubFeignClient(YahooApiProperties yahooApiProperties) {

        return Feign
                .builder()
                .client(new OkHttpClient())
                .decoder(new CsvDecoder())
                .logger(new Slf4jLogger(YahooFeignInterface.class))
                .logLevel(Logger.Level.FULL)
                .target(YahooFeignInterface.class, yahooApiProperties.apiUrl());
    }

}
