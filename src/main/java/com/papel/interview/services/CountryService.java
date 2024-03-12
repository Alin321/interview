package com.papel.interview.services;

import com.papel.interview.domain.entities.City;
import com.papel.interview.domain.entities.Country;
import com.papel.interview.domain.models.CityDTO;
import com.papel.interview.domain.models.CountryDTO;
import com.papel.interview.exceptions.EntityAlreadyModifiedException;
import com.papel.interview.exceptions.ObjectNotFoundException;
import com.papel.interview.repositories.CityRepository;
import com.papel.interview.repositories.CountryRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.papel.interview.exceptions.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CountryDTO saveCountry(CountryDTO country) {
        var entity = modelMapper.map(country, Country.class);
        var result = countryRepository.save(entity);
        return modelMapper.map(result, CountryDTO.class);
    }

    public Page<CountryDTO> getCountries(int pageSize, int pageNumber) {
        var pageable = PageRequest.of(pageNumber, pageSize);
        return countryRepository.findAll(pageable).map(entity -> modelMapper.map(entity, CountryDTO.class));
    }

    public CountryDTO getCountryById(Long id) {
        return modelMapper.map(retrieveCountryFromDB(id), CountryDTO.class);
    }

    public void deleteCountry(Long id) {
        countryRepository.delete(retrieveCountryFromDB(id));
    }

    public List<CityDTO> getCitiesForCountry(Long countryId) {
        var country = retrieveCountryFromDB(countryId);
        return country.getCities().stream().map(entity -> modelMapper.map(entity, CityDTO.class)).toList();
    }

    public CityDTO addCityToCountry(Long countryId, CityDTO cityDTO) {
        var country = retrieveCountryFromDB(countryId);
        var city = modelMapper.map(cityDTO, City.class);
        city.setCountry(country);
        return modelMapper.map(cityRepository.save(city), CityDTO.class);
    }

    public CountryDTO updateCountry(Long id, CountryDTO country) {
        var entity = retrieveCountryFromDB(id);
        modelMapper.map(country, entity);
        return modelMapper.map(countryRepository.save(entity), CountryDTO.class);
    }

    private Country retrieveCountryFromDB(Long id) {
        return countryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(INT_0001));
    }
}
