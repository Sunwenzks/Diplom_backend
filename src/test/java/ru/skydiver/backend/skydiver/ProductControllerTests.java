package ru.skydiver.backend.skydiver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import config.FunctionalTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerTests extends FunctionalTest {


    @Test
    @DatabaseSetup(value = {
           "CategoryData.xml",
           "ProductDataData.xml"
    })
    @Disabled
    public void getProductByIdTest() throws Exception {
        var expected =
                "{\"id\":1,\"name\":\"p1\",\"categoryId\":1,\"price\":1,\"productImage\":null," +
                "\"productDescription\":\"dec1\"}";
        var actual = client.send(
                HttpRequest.newBuilder()
                        .uri(new URI(host + port + "/product/info?productId=1"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString());
        assertThat(actual.statusCode()).isEqualTo(200);
        assertThat(actual.body()).isEqualTo(expected);
    }

    @Test
    @Disabled
    public void addProduct() throws IOException, InterruptedException {
        String encoding = Base64.getEncoder().encodeToString(("user" + ":" + "password").getBytes());
        var result = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/token"))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding)
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        assertThat(result.body()).isNotNull();
        var token = result.body();
        var expected = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/admin/add-product"))
                        .POST(HttpRequest.BodyPublishers.ofString(
                                "{\"name\":\"фигня\",\"price\":\"45\",\"description\":\"фигня\",\"categoryId\":\"6\"," +
                                        "\"picture\":\"фигня\"}"
                        ))
                        .setHeader("content-type", "application/json")
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }
}
