package pl.romczaj.marketnotes.common.mvc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;

@Component
public class StockCompanyExternalIdMvcConverter implements Converter<String, StockCompanyExternalId> {

    @Override
    public StockCompanyExternalId convert(String source) {
        return StockCompanyExternalId.fromString(source);
    }
}