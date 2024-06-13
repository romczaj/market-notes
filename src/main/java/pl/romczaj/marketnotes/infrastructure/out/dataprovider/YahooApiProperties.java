package pl.romczaj.marketnotes.infrastructure.out.dataprovider;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "yahoo")
@Validated
record YahooApiProperties(
        @NotBlank @URL String apiUrl) {
}
