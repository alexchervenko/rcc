package io.student.rococo.geo.service.impl;

import io.student.rococo.geo.data.entity.CountryEntity;
import io.student.rococo.geo.data.repository.CountryRepository;
import io.student.rococo.geo.exception.ResourceNotFoundException;
import io.student.rococo.geo.model.CountryJson;
import io.student.rococo.geo.service.api.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    
    private final CountryRepository countryRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Page<CountryJson> getAllCountries(String name, Pageable pageable) {
        Page<CountryEntity> countries = (name == null)
                ? countryRepository.findAll(pageable)
                : countryRepository.findAllByNameContainsIgnoreCase(name, pageable);
        return countries.map(this::toJson);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CountryJson getCountryByName(String name) {
        CountryEntity entity = countryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with name: " + name));
        return toJson(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CountryJson getCountryById(String id) {
        CountryEntity entity = countryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id: " + id));
        return toJson(entity);
    }
    
    private CountryJson toJson(CountryEntity entity) {
        return new CountryJson(entity.getId(), entity.getName());
    }
}