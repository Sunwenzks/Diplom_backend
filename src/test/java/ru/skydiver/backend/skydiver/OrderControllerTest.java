package ru.skydiver.backend.skydiver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import config.FunctionalTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderControllerTest extends FunctionalTest {
    @Test
    @DatabaseSetup(value = {
            "CategoryData.xml",
            "ProductDataData.xml",
            "CartData.xml"
    })
    public void createOrderTest() throws IOException, InterruptedException {
        var token = getToken();
        var actual = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/order/create"))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build(),
                HttpResponse.BodyHandlers.ofString());
        assertThat(actual.statusCode()).isEqualTo(200);
    }
}
