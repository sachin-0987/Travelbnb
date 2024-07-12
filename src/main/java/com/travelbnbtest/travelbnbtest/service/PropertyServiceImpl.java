package com.travelbnbtest.travelbnbtest.service;


import com.travelbnbtest.travelbnbtest.entity.Country;
import com.travelbnbtest.travelbnbtest.entity.Location;
import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.PropertyDto;
import com.travelbnbtest.travelbnbtest.repository.CountryRepository;
import com.travelbnbtest.travelbnbtest.repository.LocationRepository;
import com.travelbnbtest.travelbnbtest.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService{

    private PropertyRepository propertyRepository;
    private LocationRepository locationRepository;
    private CountryRepository countryRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, LocationRepository locationRepository, CountryRepository countryRepository) {
        this.propertyRepository = propertyRepository;
        this.locationRepository = locationRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public PropertyDto addProperty(long locationId, long countryId, PropertyDto dto) {
        Optional<Location> byId = locationRepository.findById(locationId);
        if (byId.isPresent()){
            Location location = byId.get();
            dto.setLocation(location);
        }else {
            throw new ResourceNotFoundException("Location not found with id: "+locationId);
        }
        Optional<Country> byId1 = countryRepository.findById(countryId);
        if (byId1.isPresent()){
            Country country = byId1.get();
            dto.setCountry(country);
        }else {
            throw new ResourceNotFoundException("Country not found with id: "+countryId);
        }
        Property property = dtoToEntity(dto);
        
        Property save = propertyRepository.save(property);
        PropertyDto dto1 = entityToDto(save);
        return dto1;

    }

    @Override
    public void deleteProperty(long propertyId) {
        propertyRepository.deleteById(propertyId);
    }

    @Override
    public PropertyDto getPropertyById(long propertyId) {
        Optional<Property> byId = propertyRepository.findById(propertyId);
        if (byId.isPresent()){
            Property property = byId.get();
            PropertyDto dto = entityToDto(property);
            return dto;
        }else {
            throw new ResourceNotFoundException("Property not found with id: "+propertyId);
        }
    }

    @Override
    public List<PropertyDto> getAllProperty(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable=null;
        if (sortDir.equalsIgnoreCase("asc")){
             pageable= PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        }else if (sortDir.equalsIgnoreCase("desc")){
            pageable= PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }else {
            pageable=PageRequest.of(pageNo,pageSize);
        }
        Page<Property> all = propertyRepository.findAll(pageable);
        List<Property> content = all.getContent();
        List<PropertyDto> collect = content.stream().map(e -> entityToDto(e)).collect(Collectors.toList());
        return collect;

    }

    @Override
    public PropertyDto updateProperty(long propertyId, long locationId, long countryId, PropertyDto propertyDto) {
        Property property=null;
        property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new ResourceNotFoundException("Property not found with id: "+propertyId)
        );

        Location location = locationRepository.findById(locationId).orElseThrow(
                ()->new ResourceNotFoundException("Location not found with id: "+locationId)
        );
        propertyDto.setLocation(location);

        Country country = countryRepository.findById(countryId).orElseThrow(
                () -> new ResourceNotFoundException("Country not found with id: " + countryId)
        );
        propertyDto.setCountry(country);

        property=dtoToEntity(propertyDto);
        property.setId(propertyId);

        Property save = propertyRepository.save(property);
        PropertyDto dto = entityToDto(save);
        return dto;
    }


    //dto to entity
    Property dtoToEntity(PropertyDto dto){
        Property property=new Property();
        property.setName(dto.getName());
        property.setNoGuests(dto.getNoGuests());
        property.setPrice(dto.getPrice());
        property.setNoBedrooms(dto.getNoBedrooms());
        property.setNoBathrooms(dto.getNoBathrooms());
        property.setLocation(dto.getLocation());
        property.setCountry(dto.getCountry());
        return property;
    }
    //entity to dto
    PropertyDto entityToDto(Property property){
        PropertyDto dto=new PropertyDto();
        dto.setId(property.getId());
        dto.setName(property.getName());
        dto.setNoBedrooms(property.getNoBedrooms());
        dto.setNoBathrooms(property.getNoBathrooms());
        dto.setPrice(property.getPrice());
        dto.setNoGuests(property.getNoGuests());
        dto.setCountry(property.getCountry());
        dto.setLocation(property.getLocation());
        return dto;
    }
}
