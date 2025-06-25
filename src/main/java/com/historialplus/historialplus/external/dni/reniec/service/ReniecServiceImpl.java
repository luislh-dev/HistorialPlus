package com.historialplus.historialplus.external.dni.reniec.service;

import com.historialplus.historialplus.external.dni.reniec.dto.ReniecResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Service
public class ReniecServiceImpl implements ReniecService {

    @Value("${reniec.api.url}")
    private String apiUrl;

    @Value("${reniec.api.token}")
    private String apiToken;

    @Override
    public Optional<ReniecResponseDto> getPersonData(String dni) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ReniecResponseDto> response = restTemplate.exchange(apiUrl + "?numero=" + dni, GET, entity, ReniecResponseDto.class);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            if ((e.getStatusCode().is4xxClientError() && e.getResponseBodyAsString().contains("not found")) || e.getStatusCode().value() == 422) {
                return Optional.empty();
            }
            throw e;
        }
    }
}
