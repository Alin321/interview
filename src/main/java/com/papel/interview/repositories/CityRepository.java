package com.papel.interview.repositories;

import com.papel.interview.domain.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
