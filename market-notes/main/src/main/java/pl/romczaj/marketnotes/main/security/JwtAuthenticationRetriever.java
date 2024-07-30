package pl.romczaj.marketnotes.main.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.internalapi.AuthenticationRetriever;

@Slf4j
@Component
public class JwtAuthenticationRetriever implements AuthenticationRetriever {

    private static final String EMAIL_CLAIM = "email";
    private static final String NAME_CLAIM = "name";
    private static final String SUB_CLAIM = "sub";

    @Override
    public LoggedUser loggedUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt principal = (Jwt) authentication.getPrincipal();
            return mapLoggedUser(principal);
        } catch (Exception e) {
            throw new RuntimeException("Cannot retrieve user's authentication");
        }
    }

    private LoggedUser mapLoggedUser(Jwt jwt) {
        Object subObject = jwt.getClaim(SUB_CLAIM);
        UserAccountExternalId userAccountExternalId = UserAccountExternalId.fromString(subObject.toString());
        return new LoggedUser(
                jwt.getClaim(EMAIL_CLAIM).toString(),
                userAccountExternalId
        );
    }
}
