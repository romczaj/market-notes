package pl.romczaj.marketnotes.main.configration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.romczaj.marketnotes.common.id.StringFormatField;

import static pl.romczaj.marketnotes.main.configration.rest.StringFormatFieldConfigureList.OBJECT_LIST;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        OBJECT_LIST.forEach(o -> {
            Class<StringFormatField> type = (Class<StringFormatField>) o.getType();
            Converter<String, StringFormatField> mvcConvert = (Converter<String, StringFormatField>) o.toMvcConvert();
            registry.addConverter(String.class, type, mvcConvert);
        });
    }
}