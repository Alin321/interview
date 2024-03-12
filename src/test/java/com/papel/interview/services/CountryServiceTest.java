package com.papel.interview.services;

import com.papel.interview.domain.entities.Country;
import com.papel.interview.domain.models.CountryDTO;
import com.papel.interview.exceptions.EntityAlreadyModifiedException;
import com.papel.interview.exceptions.ErrorCode;
import com.papel.interview.exceptions.ObjectNotFoundException;
import com.papel.interview.repositories.CityRepository;
import com.papel.interview.repositories.CountryRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    private CountryService service;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    public void init() {
        service = new CountryService(countryRepository, cityRepository, new ModelMapper());
    }

    @Test
    void givenWrongId_whenDeleteCountry_thenExceptionIsThrown() {
        // setup
        var id = 1L;
        when(countryRepository.findById(eq(id))).thenReturn(Optional.empty());

        // execute
        var exception = catchThrowable(() -> service.deleteCountry(id));

        // verify
        verify(countryRepository).findById(eq(id));
        assertThat(exception)
                .isInstanceOf(ObjectNotFoundException.class)
                .matches(p -> ((ObjectNotFoundException) p).getCode() == ErrorCode.INT_0001);
    }

    @Test
    void givenCorrectId_whenDeleteCountry_thenRepositoryDeleteIsCalled() {
        // setup
        var id = 1L;
        var country = new Country();
        when(countryRepository.findById(eq(id))).thenReturn(Optional.of(country));

        // execute
        service.deleteCountry(id);

        // verify
        verify(countryRepository).findById(eq(id));
        verify(countryRepository).delete(country);
    }

}