package ru.skydiver.backend.skydiver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import config.FunctionalTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

public class CartControllerTests extends FunctionalTest {

    @Test
    @DatabaseSetup(value = {
            "CategoryData.xml",
            "ProductDataData.xml"
    })
    @ExpectedDatabase(value = "CartData.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void AddToCart() throws IOException, InterruptedException {
        var token = getToken();
        var actual = addToCart(token);
        assertThat(actual.statusCode()).isEqualTo(200);
    }
    @Test
    @DatabaseSetup(value = {
            "CategoryData.xml",
            "ProductDataData.xml"
    })
    public void  getCart() throws IOException, InterruptedException {
        var token = getToken();
        var expected = "{\"products\":[{\"id\":2,\"name\":\"p2\",\"price\":2,\"productImage\":null,\"amount\":3}]}";
        addToCart(token);
        var actual = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/cart/getCart"))
                        .GET()
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        assertThat(actual.statusCode()).isEqualTo(200);
        assertThat(actual.body()).isEqualTo(expected);
    }

    private HttpResponse<String> addToCart(String token) throws IOException, InterruptedException {
        var request =
                """
                        {
                          "productId": 2,
                          "amount": 3
                        }""";
        return client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/cart/add"))
                        .POST(HttpRequest.BodyPublishers.ofString(request))
                        .setHeader("content-type", "application/json")
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private String getToken() throws IOException, InterruptedException {
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
