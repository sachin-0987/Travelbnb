package com.travelbnbtest.travelbnbtest.repository;

import com.travelbnbtest.travelbnbtest.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}