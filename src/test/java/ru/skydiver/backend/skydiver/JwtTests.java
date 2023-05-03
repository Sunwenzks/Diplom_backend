package ru.skydiver.backend.skydiver;

import java.io.IOException;
import java.net.URI;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import config.FunctionalTest;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;


import static org.assertj.core.api.Assertions.assertThat;

public class JwtTests extends FunctionalTest {
    @Test
    void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
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
    }

    @Test
    void validateToken() throws IOException, InterruptedException {
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
                        .uri(URI.create(host + port + "/info"))
                        .GET()
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        assertThat(expected.body()).isNotNull().isEqualTo("Страничка работает user");
    }

    @Test
    void correct401WithToken() throws IOException, InterruptedException {
        var expected = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/info"))
                        .GET()
                        .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "eyJhbGciOiJSUzI1NyJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidXNlciIsImV4cCI6MTY4MzE2MTk4MCwiaWF0IjoxNjgzMTI1OTgwLCJzY29wZSI6ImFwcCJ9.vpgjGNrU2riMgxQDsyRCJakt2EJFMCN2BdRabjjIGSgluehLheku4rN2TbwdQE4DyaOL2fQlEws4a-TlLdmxAeZBOPdwO1nZ48LcX8KHi5qrETKkxrTLYVB2PMH5-0YqYU_ojAqYhO4PAH5JBtHrtTpPpHe7kq4nrK9Yob6Q_k36PvH3aOqrv7AMju2axWhokY1cE1z-Y4er-XyfUtDFItvib1EHi42XJxG2uJEEtOJMiGeugaFxnQv9DXxeRseEsY_qmXZCbPXz-ltwXEMnPYu2fswTZu7Dwo1emKEKOvL_9IoDv3_A7bPPRNgF4n3rt3v248RLKbiOpp3drNKo9g")
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        assertThat(expected.statusCode()).isEqualTo(401);
    }

    @Test
    void correct401WithoutToken() throws IOException, InterruptedException {
        var expected = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(host + port + "/info"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );
        assertThat(expected.statusCode()).isEqualTo(401);
    }
}
