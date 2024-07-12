package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.Country;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.CountryDto;
import com.travelbnbtest.travelbnbtest.repository.CountryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    public CountryDto addCountry(CountryDto dto) {
        Country country = dtoToEntity(dto);
        Country save = countryRepository.save(country);
        CountryDto dto1 = entityToDto(save);
        return dto1;
    }

    //dto to entity
    Country dtoToEntity(CountryDto dto) {
        Country country = new Country();
        country.setName(dto.getName());
        return country;
    }

    //entity to dto
    CountryDto entityToDto(Country country) {
        CountryDto dto = new CountryDto();
        dto.setId(country.getId());
        dto.setName(country.getName());
        return dto;
    }

    @Override
    public void deleteCountry(long countryId) {
        countryRepository.deleteById(countryId);
    }

    @Override
    public CountryDto getCountryRecordById(long countryId) {
        Optional<Country> byId = countryRepository.findById(countryId);
        if (byId.isPresent()) {
            Country country = byId.get();
            CountryDto dto = entityToDto(country);
            return dto;
        } else {
            throw new ResourceNotFoundException("Country not exist with id: " + countryId);
        }
    }

    @Override
    public List<CountryDto> getAllCountryRecord(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else if (sortDir.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize);
        }
        Page<Country> all = countryRepository.findAll(pageable);
        List<Country> content = all.getContent();
        List<CountryDto> collect = content.stream().map(e -> entityToDto(e)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CountryDto updateCountry(long countryId, CountryDto countryDto) {
        Country country = null;
        Optional<Country> byId = countryRepository.findById(countryId);
        if (byId.isPresent()) {
            country = byId.get();
            country = dtoToEntity(countryDto);
            country.setId(countryId);

            Country save = countryRepository.save(country);
            CountryDto dto = entityToDto(save);
            return dto;
        } else {
            throw new ResourceNotFoundException("country is not exist with id: "+countryId);
        }

    }
}