package io.student.rococo.service.impl;

import io.student.rococo.model.CountryJson;
import io.student.rococo.service.api.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class RestCountryClient implements CountryService {

    private final RestTemplate restTemplate;

    @Value("${geo.service.url}")
    private String geoServiceUrl;

    @Override
    public Page<CountryJson> all(String name, Pageable pageable) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(geoServiceUrl)
                .path("/api/countries")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());
        if (name != null) {
            builder.queryParam("name", name);
        }

        URI uri = builder.build().encode().toUri();

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders()),
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }
            );

            if (response.getBody() != null) {
                Map<String, Object> body = response.getBody();
                List<Map<String, Object>> contentMap = (List<Map<String, Object>>) body.get("content");
                List<CountryJson> countries = contentMap.stream()
                        .map(this::mapToCountry)
                        .toList();

                Number totalElements = (Number) body.get("totalElements");
                return new PageImpl<>(countries, pageable, totalElements.longValue());
            } else {
                return Page.empty(pageable);
            }

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND,
                        "Страна не найдена", e);
            }
            String body = e.getResponseBodyAsString();
            throw new RuntimeException("Ошибка при запросе к geo-сервису: " + e.getStatusCode() + " " + body, e);
        } catch (HttpServerErrorException e) {
            String body = e.getResponseBodyAsString();
            throw new RuntimeException("Ошибка на стороне geo-сервиса: " + e.getStatusCode() + " " + body, e);
        } catch (RestClientException e) {
            throw new RuntimeException("Ошибка при запросе к geo-сервису", e);
        }
    }

    @Override
    public CountryJson findCountryByName(String name) {
        URI uri = UriComponentsBuilder.fromHttpUrl(geoServiceUrl)
                .path("/api/countries/name/{name}")
                .buildAndExpand(name)
                .encode()
                .toUri();
        try {
            ResponseEntity<CountryJson> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders()),
                    CountryJson.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return null;
            }
            String body = e.getResponseBodyAsString();
            throw new RuntimeException("Страна не найдена по имени: " + name + ". Response: " + body, e);
        } catch (HttpServerErrorException e) {
            String body = e.getResponseBodyAsString();
            throw new RuntimeException("Ошибка на стороне geo-сервиса при поиске страны по имени: " + name + ". Response: " + body, e);
        } catch (RestClientException e) {
            throw new RuntimeException("Ошибка при запросе к geo-сервису", e);
        }
    }

    @Override
    public CountryJson findCountryById(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(geoServiceUrl)
                .path("/api/countries/{id}")
                .buildAndExpand(id)
                .encode()
                .toUri();
        try {
            ResponseEntity<CountryJson> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders()),
                    CountryJson.class
            );

            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return null;
            }
            String body = e.getResponseBodyAsString();
            throw new RuntimeException("Страна не найдена по id: " + id + ". Response: " + body, e);
        } catch (HttpServerErrorException e) {
            String body = e.getResponseBodyAsString();
            throw new RuntimeException("Ошибка на стороне geo-сервиса при поиске страны по id: " + id + ". Response: " + body, e);
        } catch (RestClientException e) {
            throw new RuntimeException("Ошибка при запросе к geo-сервису", e);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        // Здесь можно добавить заголовки авторизации
        // headers.set("Authorization", "Bearer ...");
        return headers;
    }

    private CountryJson mapToCountry(Map<String, Object> item) {
        return new CountryJson(
                UUID.fromString((String) item.get("id")),
                (String) item.get("name")
        );
    }
}
