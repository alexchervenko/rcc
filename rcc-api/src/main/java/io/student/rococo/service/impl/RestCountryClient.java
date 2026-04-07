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
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
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
        String url = geoServiceUrl + "/api/countries";
        if (name != null) {
            url += "?name=" + name;
        }
        url += (name != null ? "&" : "?") + "page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize();
        
        try {
            URI uri = URI.create(url);
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders()),
                    new ParameterizedTypeReference<Map<String, Object>>() {}
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
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при запросе к geo-сервису", e);
        }
    }
    
    @Override
    public CountryJson findCountryByName(String name) {
        try {
            URI uri = URI.create(geoServiceUrl + "/api/countries/name/" + name);
            
            ResponseEntity<CountryJson> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders()),
                    CountryJson.class
            );
            
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Страна не найдена по имени: " + name, e);
        }
    }
    
    @Override
    public CountryJson findCountryById(String id) {
        try {
            URI uri = URI.create(geoServiceUrl + "/api/countries/" + id);
            
            ResponseEntity<CountryJson> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders()),
                    CountryJson.class
            );
            
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Страна не найдена по id: " + id, e);
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


