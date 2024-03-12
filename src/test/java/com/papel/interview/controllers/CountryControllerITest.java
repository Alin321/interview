package com.papel.interview.controllers;

import com.papel.interview.domain.models.CountryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CountryControllerITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenValidRequest_whenCallingPostCountriesEndpoint_thenCountryIsReturnedWithGeneratedId() {

        // setup
        var name = "Romania";
        var population = 10000L;
        var request = CountryDTO.builder().name(name).population(population).build();

        // execute
        var response = restTemplate.postForEntity(STR. "http://localhost:\{ port }/api/countries" , request, CountryDTO.class);

        // verify
        var expectedCountryResponse = CountryDTO.builder().id(1L).name(name).population(population).build();
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).usingRecursiveAssertion().isEqualTo(expectedCountryResponse);

    }
}