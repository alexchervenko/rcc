package io.student.rococo.service.impl;

import io.student.rococo.model.UserJson;
import io.student.rococo.service.api.UsersClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class UsersApiClient implements UsersClient {

    private final RestTemplate restTemplate;

    @Value("${users.service.url}")
    private String usersServiceUrl;

    @Override
    public UserJson getCurrentUser() {
        URI uri = URI.create(usersServiceUrl + "/api/users/current");

        ResponseEntity<UserJson> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                UserJson.class);

        return response.getBody();
    }

    @Override
    public UserJson updateUserProfile(UserJson user) {
        URI uri = URI.create(usersServiceUrl + "/api/users/profile");

        ResponseEntity<UserJson> response = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                new HttpEntity<>(user, createHeaders()),
                UserJson.class);

        return response.getBody();
    }

    @Override
    public UserJson getUserById(String userId) {
        URI uri = URI.create(usersServiceUrl + "/api/users/" + userId);

        ResponseEntity<UserJson> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                UserJson.class);

        return response.getBody();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        // Здесь можно добавить заголовки авторизации
        // headers.set("Authorization", "Bearer ...");
        return headers;
    }
}
