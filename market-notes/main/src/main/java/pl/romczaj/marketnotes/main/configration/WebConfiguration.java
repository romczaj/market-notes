package pl.romczaj.marketnotes.main.configration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.romczaj.marketnotes.common.id.StringOneLine;

import static pl.romczaj.marketnotes.main.configration.rest.StringOneLineConfigureList.OBJECT_REGISTERS;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        OBJECT_REGISTERS.forEach(o -> {
            Class<StringOneLine> type = (Class<StringOneLine>) o.getType();
            Converter<String, StringOneLine> mvcConvert = (Converter<String, StringOneLine>) o.toMvcConvert();
            registry.addConverter(String.class, type, mvcConvert);
        });
    }
}