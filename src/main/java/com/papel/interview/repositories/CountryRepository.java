package com.papel.interview.repositories;

import com.papel.interview.domain.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
