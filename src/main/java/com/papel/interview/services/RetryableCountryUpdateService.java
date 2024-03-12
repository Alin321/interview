package com.papel.interview.services;

import com.papel.interview.domain.models.CountryDTO;
import com.papel.interview.exceptions.EntityAlreadyModifiedException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetryableCountryUpdateService {

    private final CountryService countryService;

    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 2, backoff = @Backoff(delay = 500, multiplier = 1.5))
    public CountryDTO updateCountry(Long id, CountryDTO country) {
        return countryService.updateCountry(id, country);
    }

    @Recover
    public CountryDTO updateCountryRecover(OptimisticLockException exception, Long id, CountryDTO countryDTO) {
        log.error(STR. "Optimistic lock exception for entity of type \{ countryDTO.getClass().getSimpleName() } and id \{ id }" , exception);
        throw new EntityAlreadyModifiedException();
    }

    @Recover
    public CountryDTO recover(RuntimeException exception) {
        // rethrow exception as it will be caught further up the chain
        throw exception;
    }

}
