package pl.romczaj.marketnotes.keycloak.eventlistener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import org.jboss.logging.Logger;

public class SimpleHttpClient {

    private static final Logger log = Logger.getLogger(SimpleHttpClient.class);

    public void sendEvent(String message, String endpoint) {
        String url = System.getenv("API_URL") + endpoint;
        String username = System.getenv("API_USERNAME");
        String password = System.getenv("API_PASSWORD");

        try {
            URL apiUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeaderValue = "Basic " + new String(encodedAuth);
            connection.setRequestProperty("Authorization", authHeaderValue);

            try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
                dataOutputStream.writeBytes(message);
                dataOutputStream.flush();
            }

            int responseCode = connection.getResponseCode();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                log.info(String.format("Response from backend %s %s", responseCode, response));
            }

            connection.disconnect();

        } catch (Exception e) {
            log.error("Failed connection to backend", e);
        }
    }
}
