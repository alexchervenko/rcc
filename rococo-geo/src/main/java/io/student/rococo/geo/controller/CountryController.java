package io.student.rococo.geo.controller;

import io.student.rococo.geo.model.CountryJson;
import io.student.rococo.geo.service.api.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    
    private final CountryService countryService;
    
    @GetMapping
    public ResponseEntity<Page<CountryJson>> getAllCountries(
            @RequestParam(required = false) String name,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CountryJson> countries = countryService.getAllCountries(name, pageable);
        return ResponseEntity.ok(countries);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<CountryJson> getCountryByName(@PathVariable String name) {
        CountryJson country = countryService.getCountryByName(name);
        return ResponseEntity.ok(country);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CountryJson> getCountryById(@PathVariable String id) {
        CountryJson country = countryService.getCountryById(id);
        return ResponseEntity.ok(country);
    }
}