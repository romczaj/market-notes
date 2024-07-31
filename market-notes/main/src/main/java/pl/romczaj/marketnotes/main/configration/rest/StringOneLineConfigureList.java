package pl.romczaj.marketnotes.main.configration.rest;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.common.id.StringOneLine;

import java.util.List;

public class StringOneLineConfigureList {

    public static final List<StringOneLineConfigure<? extends StringOneLine>> OBJECT_REGISTERS = List.of(
            new StringOneLineConfigure<>(
                    StockCompanyExternalId.class,
                    StockCompanyExternalId::fromString,
                    StockCompanyExternalId::toString
            ),
            new StringOneLineConfigure<>(
                    UserAccountExternalId.class,
                    UserAccountExternalId::fromString,
                    UserAccountExternalId::toString
            )
    );
}
