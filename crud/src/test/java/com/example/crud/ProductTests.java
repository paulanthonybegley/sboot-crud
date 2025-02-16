package com.example.crud;

import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductTests {
    @Value(value="${local.server.port}")
    private int port;

    @Test
    public void givenAProduct_WhenItIsCreated() throws Exception{
        var response = WebTestClient
            .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
            .post()
                .uri("/products")
            .exchange();

            itShouldCreateANewProduct(response);
            itShouldAllocateANewId(response);

    }

    private void itShouldCreateANewProduct(ResponseSpec response){
        response.expectStatus()
            .isCreated();
    }

    private void itShouldAllocateANewId(ResponseSpec response){
        response
            .expectBody(ProductResponse.class)
                .value(product -> {
                    assertThat(product.getId()).isNotEqualTo(new UUID(0, 0));
					assertThat(product.getId()).isNotNull();

            });
    }
}
