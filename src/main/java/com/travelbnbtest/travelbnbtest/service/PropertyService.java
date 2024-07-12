package com.travelbnbtest.travelbnbtest.service;


import com.travelbnbtest.travelbnbtest.payload.PropertyDto;

import java.util.List;

public interface PropertyService {
    PropertyDto addProperty(long locationId, long countryId, PropertyDto dto);

    void deleteProperty(long propertyId);

    PropertyDto getPropertyById(long propertyId);

    List<PropertyDto> getAllProperty(int pageSize, int pageNo, String sortBy, String sortDir);

    PropertyDto updateProperty(long propertyId, long locationId, long countryId, PropertyDto propertyDto);
}
