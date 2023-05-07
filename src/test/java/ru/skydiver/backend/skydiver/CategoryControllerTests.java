package ru.skydiver.backend.skydiver;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import config.FunctionalTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryControllerTests extends FunctionalTest {


    @Test
    @DatabaseSetup("CategoryData.xml")
    @DatabaseTearDown(value = "CategoryData.xml", type = DatabaseOperation.DELETE)
    public void getAllCategories() throws Exception {
        var expected = "{\"categories\":" +
                "[{\"id\":1,\"name\":\"First\",\"mainCategory\":{\"present\":true}," +
                "\"imageUrl\":{\"present\":true}},{\"id\":2,\"name\":\"Second\",\"mainCategory\":{\"present\":true}," +
                "\"imageUrl\":{\"present\":true}}]}";

        var actual = client.send(HttpRequest.newBuilder().uri(URI.create(host + port + "/category/list"))
                .build(), HttpResponse.BodyHandlers.ofString());

        assertThat(actual.body()).isEqualTo(expected);
    }

    @Test
    @DatabaseSetup("CategoryData.xml")
    @DatabaseTearDown(value = "CategoryData.xml", type = DatabaseOperation.DELETE)
    public void getMainPageCategories() throws Exception {
        var expected = "{\"categories\":[{\"id\":1,\"name\":\"First\",\"mainCategory\":{\"present\":true}," +
                "\"imageUrl\":{\"present\":true}}]}";
        var actual = client.send(HttpRequest.newBuilder().uri(
                URI.create(host + port + "/category/list?isMain=true"))
                .build(), HttpResponse.BodyHandlers.ofString());

        assertThat(actual.body()).isEqualTo(expected);
    }
}
