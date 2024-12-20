package com.historialplus.historialplus.external.ce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.historialplus.historialplus.external.ce.dto.CeResponseDto;
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
public class CeServiceImpl implements ICeService {

    @Value("${cee.api.url}")
    private String apiUrl;

    @Value("${cee.api.token}")
    private String apiToken;

    @Override
    public Optional<CeResponseDto> getCeeData(String ceeNumber) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String url = apiUrl + "/" + ceeNumber + "?apikey=" + apiToken;
            ResponseEntity<String> response = restTemplate.exchange(url, GET, entity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode body = root.path("body");
            CeResponseDto ceeResponseDto = objectMapper.treeToValue(body, CeResponseDto.class);
            return Optional.ofNullable(ceeResponseDto);
        } catch (HttpClientErrorException e) {
            if ((e.getStatusCode().is4xxClientError() && e.getResponseBodyAsString().contains("not found")) || e.getStatusCode().value() == 422 || e.getStatusCode().value() == 400) {
                return Optional.empty();
            }
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }
}
