package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

@Converter(autoApply = true)
public class StockCompanyExternalIdConverter implements AttributeConverter<StockCompanyExternalId, String> {

    @Override
    public String convertToDatabaseColumn(StockCompanyExternalId attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public StockCompanyExternalId convertToEntityAttribute(String dbData) {
        return dbData != null ? StockCompanyExternalId.fromString(dbData) : null;
    }
}