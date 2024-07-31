package pl.romczaj.marketnotes.main.configration.rest;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.common.id.StringFormatField;

import java.util.List;

public class StringFormatFieldConfigureList {

    public static final List<StringFormatFieldConfigure<? extends StringFormatField>> OBJECT_LIST = List.of(
            new StringFormatFieldConfigure<>(
                    StockCompanyExternalId.class,
                    StockCompanyExternalId::fromString,
                    StockCompanyExternalId::toString
            ),
            new StringFormatFieldConfigure<>(
                    UserAccountExternalId.class,
                    UserAccountExternalId::fromString,
                    UserAccountExternalId::toString
            )
    );
}
