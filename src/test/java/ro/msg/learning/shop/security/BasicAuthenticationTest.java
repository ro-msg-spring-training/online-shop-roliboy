package ro.msg.learning.shop.security;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "with-basic"})
@WebAppConfiguration
class BasicAuthenticationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void unrestrictedEndpointAllowsUnauthorizedRequests() throws Exception {
        this.mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void restrictedEndpointDisallowsUnauthorizedRequests() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void restrictedEndpointAllowsAuthorizedRequests() throws Exception {
        var credentials = "Basic " + new String(Base64.encodeBase64(("admin:admin").getBytes()));
        this.mockMvc.perform(get("/users")
                .header("Authorization", credentials))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
