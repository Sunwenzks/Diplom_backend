package ru.skydiver.backend.skydiver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import config.FunctionalTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationControllerTest extends FunctionalTest {

    @Test
    @ExpectedDatabase(
            value = "RegisterControllerTest.after.xml",
    assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void registerNewUser() throws IOException, InterruptedException {
        var actual = client.send(HttpRequest.newBuilder()
                .uri(URI.create(host + port + "/registration/register"))
                .POST(HttpRequest.BodyPublishers.ofString(
                            "{\n" +
                                  "\"userName\": \"user2\",\n" +
                                  "\"password\": \"pass\",\n" +
                                  "\"email\": \"mail@mail.ru\"\n" +
                                  "}"))
                .setHeader("content-type", "application/json")
                .build(), HttpResponse.BodyHandlers.ofString());
        assertThat(actual.statusCode()).isEqualTo(200);
    }
}
