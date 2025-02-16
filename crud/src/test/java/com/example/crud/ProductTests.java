package com.example.crud;

import java.net.URI;
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

    private static final String PRODUCT_PATH = "/products";

    private String baseUri() {
        return "http://localhost:" + port;
    }

    private WebTestClient newWebClient(){
        return WebTestClient
            .bindToServer()
            .baseUrl(baseUri())
            .build();

    }

    private ResponseSpec createProduct(CreateProductRequest productRequest){
        return newWebClient()
            .post()
                .uri(PRODUCT_PATH)
                .bodyValue(productRequest)
            .exchange();
    }

    @Test
    public void givenAProduct_WhenItIsCreated() throws Exception {
        CreateProductRequest productRequest = new CreateProductRequest("Test Product");
        var response = createProduct(productRequest);

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
                .location(baseUri() + PRODUCT_PATH + "/" + newProduct.getId());
    }

    @Test
    public void givenAProductIsCreated_WhenCheckingItsDetails() {
        CreateProductRequest productRequest = new CreateProductRequest("Test Product");
        URI newProductLocation = createProduct(productRequest)
                .expectBody(ProductResponse.class)
                .returnResult()
                .getResponseHeaders().getLocation();

        ResponseSpec response = newWebClient()
                .get().uri(newProductLocation)
                .exchange();

        itShouldFindTheNewProduct(response);
    }

    private void itShouldFindTheNewProduct(ResponseSpec response) {
        response.expectStatus()
            .isOk();
    }
}
