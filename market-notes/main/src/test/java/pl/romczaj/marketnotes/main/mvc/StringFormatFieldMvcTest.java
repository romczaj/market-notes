package pl.romczaj.marketnotes.main.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.romczaj.marketnotes.main.configration.ObjectMapperConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = StringFormatFieldController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class})
@Slf4j
@Import(ObjectMapperConfiguration.class)
class StringFormatFieldMvcTest {

    @Autowired
    private MockMvc mockMvc;

    static final String RBW_WSE = "RBW_WSE";
    static final String DEFAULT_ACCOUNT_ID_STRING = "e3837b9a-1c3a-4a17-8e5d-189c81f8f59a";


    @Test
    void shouldCorrectSerializeJson() throws Exception {
        mockMvc.perform(get("/json-response"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.stockCompanyExternalId").value(RBW_WSE))
                .andExpect(jsonPath("$.userAccountExternalId").value(DEFAULT_ACCOUNT_ID_STRING))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldCorrectDeserializeJson() throws Exception {

        String jsonBody = String.format("""
                {
                  "stockCompanyExternalId": "%s",
                  "userAccountExternalId": "%s"
                }
                """, RBW_WSE, DEFAULT_ACCOUNT_ID_STRING);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/json-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                )
                .andExpect(jsonPath("$.stockCompanyExternalId").value(RBW_WSE))
                .andExpect(jsonPath("$.userAccountExternalId").value(DEFAULT_ACCOUNT_ID_STRING))
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldCorrectDeserializeRequestParam() throws Exception {
        mockMvc.perform(get("/request-param")
                        .param("stockCompanyExternalId", RBW_WSE)
                        .param("userAccountExternalId", DEFAULT_ACCOUNT_ID_STRING))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.stockCompanyExternalId").value(RBW_WSE))
                .andExpect(jsonPath("$.userAccountExternalId").value(DEFAULT_ACCOUNT_ID_STRING))
                .andDo(MockMvcResultHandlers.print());
    }
}
