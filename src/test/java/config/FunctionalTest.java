package config;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "server.port=8042"
        })
@ExtendWith(SpringExtension.class)
@Import({
        DbUnitConfig.class,
        EmbeddedPostgresConfiguration.class,
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@TestPropertySource(locations="classpath:test.properties")
@DatabaseSetup("UserData.xml")
public class FunctionalTest {
    protected String host = "http://localhost";

    protected String port = ":8042";

    protected HttpClient client = HttpClient.newBuilder()
            .build();

    protected String getToken() throws IOException, InterruptedException {
        String encoding = Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes());
        return client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/token"))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding)
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        ).body();
    }
}
