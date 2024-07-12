package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.payload.CountryDto;

import java.util.List;

public interface CountryService {
    CountryDto addCountry(CountryDto dto);

    void deleteCountry(long countryId);

    CountryDto getCountryRecordById(long countryId);

    List<CountryDto> getAllCountryRecord(int pageSize, int pageNo, String sortBy, String sortDir);

    CountryDto updateCountry(long countryId, CountryDto countryDto);
}
