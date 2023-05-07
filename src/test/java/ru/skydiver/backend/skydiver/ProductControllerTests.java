package ru.skydiver.backend.skydiver;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import config.FunctionalTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerTests extends FunctionalTest {


    @Test
    @DatabaseSetup(value = {
           "CategoryData.xml",
           "ProductDataData.xml"
    })
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
}
