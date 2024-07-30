package pl.romczaj.marketnotes.useraccount.application.config;

import pl.romczaj.marketnotes.common.dto.CalculationResult;
import pl.romczaj.marketnotes.common.dto.Money;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.internalapi.AuthenticationRetriever;
import pl.romczaj.marketnotes.internalapi.AuthenticationRetriever.LoggedUser;
import pl.romczaj.marketnotes.internalapi.StockMarketInternalApi;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.email.EmailSender;

import java.util.List;

import static pl.romczaj.marketnotes.common.dto.StockMarketSymbol.WSE;

public class DefaultValues {

    public static final String DEFAULT_LOGGED_NAME = "Jan Kowalski";
    public static final String DEFAULT_LOGGED_EMAIL = "jankowalski@marketnotes.pl";
    public static final UserAccountExternalId DEFAULT_LOGGED_ACCOUNT_EXTERNAL_ID = UserAccountExternalId.generate();

    public static final LoggedUser DEFAULT_LOGGED_USER = new LoggedUser(
            DEFAULT_LOGGED_EMAIL,
            DEFAULT_LOGGED_ACCOUNT_EXTERNAL_ID);


    public static final AuthenticationRetriever DEFAULT_AUTHENTICATION_RETRIEVER = new AuthenticationRetriever() {
        @Override
        public LoggedUser loggedUser() {
            return DEFAULT_LOGGED_USER;
        }
    };

    public static final StockMarketInternalApi DEFAULT_STOCK_MARKET_INTERNAL_API = new StockMarketInternalApi() {
        @Override
        public List<StockCompanyExternalId> findAll() {
            return List.of(
                    new StockCompanyExternalId("ELK", WSE)
            );
        }

        @Override
        public StockCompanyResponse getCompanyBySymbol(StockCompanyExternalId companyExternalId) {
            return new StockCompanyResponse(
                    "Super comp",
                    companyExternalId,
                    Money.ofPln(100.0),
                    CalculationResult.empty());
        }
    };

    public static final EmailSender DEFAULT_EMAIL_SENDER = new EmailSender() {

        @Override
        public SendEmailResult sendEmail(SendEmailReportCommand sendEmailReportCommand) {
            return new EmailSender.SendEmailResult(true, "", "");
        }
    };
}
