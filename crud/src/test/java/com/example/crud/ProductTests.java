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
    @Value(value = "${local.server.port}")
    private int port;

    private String baseUri() {
        return "http://localhost:" + port;
    }

    @Test
    public void givenAProduct_WhenItIsCreated() throws Exception {
        CreateProductRequest productRequest = new CreateProductRequest("Test Product");
        var response = WebTestClient
                .bindToServer()
                .baseUrl(baseUri())
                .build()
                .post()
                    .uri("/products")
                    .bodyValue(productRequest)
                .exchange();

        itShouldCreateANewProduct(response);
        ProductResponse newProduct = itShouldAllocateANewId(response);
        itShouldShowWhereToLocateNewProduct(response, newProduct);
        itShouldConfirmProductDetails(productRequest, newProduct);

    }

    private void itShouldConfirmProductDetails(CreateProductRequest productRequest, ProductResponse newProduct) {
        assertThat(newProduct.getName()).isEqualTo(productRequest.getName());
    }

    private void itShouldCreateANewProduct(ResponseSpec response) {
        response.expectStatus()
                .isCreated();
    }

    private ProductResponse itShouldAllocateANewId(ResponseSpec response) {
        return response
                .expectBody(ProductResponse.class)
                .value(product -> {
                    assertThat(product.getId()).isNotEqualTo(new UUID(0, 0));
                    assertThat(product.getId()).isNotNull();

                })
                .returnResult()
                .getResponseBody();
    }

    private void itShouldShowWhereToLocateNewProduct(ResponseSpec response, ProductResponse newProduct) {
        response.expectHeader()
                .location(baseUri() + "/products" + "/" + newProduct.getId());
    }
}
