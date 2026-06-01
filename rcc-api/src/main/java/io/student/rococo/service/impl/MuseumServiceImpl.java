package io.student.rococo.service.impl;

import io.student.rococo.data.entity.MuseumEntity;
import io.student.rococo.data.repository.MuseumRepository;
import io.student.rococo.exception.ResourceNotFoundException;
import io.student.rococo.model.CountryJson;
import io.student.rococo.model.MuseumJson;
import io.student.rococo.service.api.CountryService;
import io.student.rococo.service.api.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MuseumServiceImpl implements MuseumService {
  private final MuseumRepository museumRepository;
  private final CountryService countryService;

  @Autowired
  public MuseumServiceImpl(MuseumRepository museumRepository, CountryService countryService) {
    this.museumRepository = museumRepository;
    this.countryService = countryService;
  }

  @Override
  public Page<MuseumJson> all(Pageable pageable) {
    return museumRepository.findAll(pageable).map(MuseumJson::fromEntity);
  }

  @Override
  public MuseumJson find(UUID id) {
    return MuseumJson.fromEntity(getRequiredMuseum(id));
  }

  @Override
  @Transactional
  public MuseumJson update(MuseumJson museum) {
    MuseumEntity museumEntity = museum.toEntity();
    // Устанавливаем countryExternalId как строку UUID если передан id
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

    // Если клиент передал только имя страны, резолвим ее и заполняем countryExternalId
    if (museum.geo().country() != null) {
      if (museum.geo().country().id() != null) {
        museumEntity.setCountryExternalId(museum.geo().country().id().toString());
      } else if (museum.geo().country().name() != null) {
        CountryJson found = countryService.findCountryByName(museum.geo().country().name());
        if (found == null || found.id() == null) {
          throw new ResourceNotFoundException("Страна не найдена: " + museum.geo().country().name());
        }
        museumEntity.setCountryExternalId(found.id().toString());
      } else {
        throw new ResourceNotFoundException("Не указаны данные страны (id или name)");
      }
    } else {
      throw new ResourceNotFoundException("Страна не указана");
    }

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
}
