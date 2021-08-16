package com.example.pizzadelivery.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PizzaEndToEndTest {

    @LocalServerPort
    private int port;

    @Test
    void getPizzasList(){
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:"+port+"/pizzas"))
                .build();
        var httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).join();

        assertThat(httpResponse.statusCode()).isEqualTo(200);

//        assertThat(httpResponse.body()).isEqualTo("[{\n" +
//                "    \"id\": 1,\n" +
//                "    \"price\": 90,\n" +
//                "    \"diameter\": 8,\n" +
//                "    \"ingredients\": \"ost,tomatsås\",\n" +
//                "    \"type\": \"Veggie\"\n" +
//                "  }\n" +
//                "]");
    }
}