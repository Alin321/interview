package com.papel.interview.services;

import com.papel.interview.domain.models.CountryDTO;
import com.papel.interview.exceptions.EntityAlreadyModifiedException;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({RetryableCountryUpdateService.class})
@EnableRetry
public class RetryableCountryUpdateServiceITest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private RetryableCountryUpdateService service;

    @Test
    void givenOptimisticLockException_whenUpdateCountry_thenAttemptToUpdateMultipleTimesBeforeException() {
        // setup
        when(countryService.updateCountry(anyLong(), any(CountryDTO.class))).thenThrow(new OptimisticLockException());

        // execute
        var exception = catchThrowable(() -> service.updateCountry(1L, CountryDTO.builder().build()));

        // verify
        verify(countryService, times(2)).updateCountry(anyLong(), any(CountryDTO.class));
        assertThat(exception).isInstanceOf(EntityAlreadyModifiedException.class);
    }
}
