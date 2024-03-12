package com.papel.interview.controllers;

import com.papel.interview.domain.models.CityDTO;
import com.papel.interview.domain.models.CountryDTO;
import com.papel.interview.services.CountryService;
import com.papel.interview.services.RetryableCountryUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final RetryableCountryUpdateService retryableCountryUpdateService;

    @Operation(summary = "Create a new country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The country was successfully created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CountryDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content)})
    @PostMapping(value = "/countries")
    public ResponseEntity<CountryDTO> saveCountry(@RequestBody @Valid CountryDTO country) {
        return new ResponseEntity<>(countryService.saveCountry(country), CREATED);
    }

    @GetMapping(value = "/countries")
    public ResponseEntity<Page<CountryDTO>> getCountries(@RequestParam @Schema(example = "10") int pageSize, @RequestParam @Schema(example = "0") int pageNumber) {
        return ResponseEntity.ok(countryService.getCountries(pageSize, pageNumber));
    }

    @GetMapping(value = "/countries/{id}")
    public ResponseEntity<CountryDTO> getCountries(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @DeleteMapping(value = "/countries/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/countries/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable Long id, @RequestBody CountryDTO country) {
        return new ResponseEntity<>(retryableCountryUpdateService.updateCountry(id, country), ACCEPTED);
    }

    @GetMapping(value = "/countries/{id}/cities")
    public ResponseEntity<List<CityDTO>> getCitiesForCountry(@PathVariable(name = "id") Long countryId) {
        return ResponseEntity.ok(countryService.getCitiesForCountry(countryId));
    }

    @PostMapping(value = "/countries/{id}/cities")
    public ResponseEntity<CityDTO> addCityToCountry(@PathVariable(name = "id") Long countryId, @RequestBody CityDTO city) {
        return new ResponseEntity<>(countryService.addCityToCountry(countryId, city), CREATED);
    }

}
