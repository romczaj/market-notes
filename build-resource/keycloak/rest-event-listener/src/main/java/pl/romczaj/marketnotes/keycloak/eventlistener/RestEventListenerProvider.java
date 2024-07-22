package pl.romczaj.marketnotes.keycloak.eventlistener;

import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;


public class RestEventListenerProvider implements EventListenerProvider {

    private static final SimpleHttpClient HTTP_CLIENT = new SimpleHttpClient();

    private final KeycloakSession session;
    private final RealmProvider model;

    public RestEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        if (adminEvent.getOperationType() == OperationType.CREATE
            && adminEvent.getResourceType() == ResourceType.USER) {

            RealmModel realm = this.model.getRealm(adminEvent.getRealmId());
            String userId = adminEvent.getResourcePath().split("/")[1];
            UserModel user = this.session.users().getUserById(realm, userId);

            String message = String.format("""
                    {
                        "email": "%s",
                        "userAccountExternalId": "%s",
                        "username": "%s"
                    }
                    """, user.getEmail(), user.getId(), user.getUsername());

            String endpoint = "/event/add-account";

            HTTP_CLIENT.sendEvent(message, endpoint);
        }
    }

    @Override
    public void close() {

    }
}
