package io.student.rcc.api;

import io.student.rcc.model.CountryJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface GeoApi {
    
    @GET("/api/countries")
    Call<List<CountryJson>> getAllCountries(
            @Query("name") String name,
            @Query("page") int page,
            @Query("size") int size
    );
    
    @GET("/api/countries/name/{name}")
    Call<CountryJson> getCountryByName(@Path("name") String name);
    
    @GET("/api/countries/{id}")
    Call<CountryJson> getCountryById(@Path("id") String id);
}
