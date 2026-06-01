package io.student.rcc.api;

import io.student.rcc.model.CountryJson;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class GeoApiClient {
    
    private final GeoApi geoApi;
    
    public GeoApiClient(String baseUrl) {
        this.geoApi = createService(GeoApi.class, baseUrl);
    }
    
    public List<CountryJson> getAllCountries(String name, int page, int size) throws IOException {
        Response<List<CountryJson>> response = geoApi.getAllCountries(name, page, size).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Failed to get countries: " + response.code());
        }
    }
    
    public CountryJson getCountryByName(String name) throws IOException {
        Response<CountryJson> response = geoApi.getCountryByName(name).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Failed to get country by name: " + response.code());
        }
    }
    
    public CountryJson getCountryById(String id) throws IOException {
        Response<CountryJson> response = geoApi.getCountryById(id).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new IOException("Failed to get country by id: " + response.code());
        }
    }
    
    private <T> T createService(Class<T> serviceClass, String baseUrl) {
        // Используем существующий RetrofitClient
        io.student.rcc.config.RetrofitClient.setBaseUrl(baseUrl);
        return io.student.rcc.config.RetrofitClient.createService(serviceClass);
    }
}
