package pl.romczaj.marketnotes.useraccount;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan
@EntityScan
@ConfigurationPropertiesScan
@EnableJpaRepositories
public class UserAccountConfiguration {
}
