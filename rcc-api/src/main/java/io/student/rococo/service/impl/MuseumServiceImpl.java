package io.student.rococo.service.impl;

import io.student.rococo.config.ServiceLocator;
import io.student.rococo.data.entity.MuseumEntity;
import io.student.rococo.data.repository.MuseumRepository;
import io.student.rococo.model.CountryJson;
import io.student.rococo.service.api.CountryService;
import io.student.rococo.exception.ResourceNotFoundException;
import io.student.rococo.model.MuseumJson;
import io.student.rococo.service.api.MuseumService;
import io.student.rococo.util.StringAsBytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MuseumServiceImpl implements MuseumService {
  private final MuseumRepository museumRepository;
  private final ServiceLocator serviceLocator;

  @Autowired
  public MuseumServiceImpl(MuseumRepository museumRepository, ServiceLocator serviceLocator) {
    this.museumRepository = museumRepository;
    this.serviceLocator = serviceLocator;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MuseumJson> all(String title, Pageable pageable) {
    Page<MuseumEntity> museums = (title == null)
        ? museumRepository.findAll(pageable)
        : museumRepository.findAllByTitleContainsIgnoreCase(title, pageable);
    return museums.map(MuseumJson::fromEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public MuseumJson findById(String id) {
    return MuseumJson.fromEntity(
        museumRepository.findById(
            UUID.fromString(id)
        ).orElseThrow(
            () -> new ResourceNotFoundException(String.format("Музей не найден по id: %s", id))
        )
    );
  }

  @Override
  @Transactional
  public MuseumJson update(MuseumJson museum) {
    MuseumEntity museumEntity = getRequiredMuseum(museum.id());
    museumEntity.setTitle(museum.title());
    museumEntity.setCity(museum.geo().city());
    museumEntity.setDescription(museum.description());
    museumEntity.setPhoto(
        new StringAsBytes(
            museum.photo()
        ).bytes()
    );
    // Устанавливаем countryExternalId как строку UUID
    if (museum.geo().country() != null && museum.geo().country().id() != null) {
      museumEntity.setCountryExternalId(museum.geo().country().id().toString());
    }
    return MuseumJson.fromEntity(
        museumRepository.save(museumEntity)
    );
  }

  @Override
  @Transactional
  public MuseumJson create(MuseumJson museum) {
    MuseumEntity museumEntity = museum.toEntity();
    
    // Проверяем существование страны через RestCountryClient
    validateCountryExists(museum.geo().country());
    
    // countryExternalId уже установлен в toEntity()
    return MuseumJson.fromEntity(
        museumRepository.save(
            museumEntity
        )
    );
  }

  private MuseumEntity getRequiredMuseum(UUID id) {
    return museumRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException(String.format("Музей не найден по id: %s", id))
    );
  }

  private void validateCountryExists(CountryJson country) {
    if (country == null) {
      throw new ResourceNotFoundException("Страна не указана");
    }
    
    CountryService countryService = serviceLocator.getCountryService();
    
    try {
      if (country.id() != null) {
        // Проверяем по ID через RestCountryClient
        countryService.findCountryById(country.id().toString());
      } else if (country.name() != null) {
        // Проверяем по имени через RestCountryClient
        countryService.findCountryByName(country.name());
      } else {
        throw new ResourceNotFoundException("Не указаны данные страны (id или name)");
      }
    } catch (Exception e) {
      throw new ResourceNotFoundException("Страна не найдена: " + 
          (country.id() != null ? country.id().toString() : country.name()));
    }
  }
}