package io.student.rococo.geo.service.api;

import io.student.rococo.geo.model.CountryJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {
    Page<CountryJson> getAllCountries(String name, Pageable pageable);
    
    CountryJson getCountryByName(String name);
    
    CountryJson getCountryById(String id);
}