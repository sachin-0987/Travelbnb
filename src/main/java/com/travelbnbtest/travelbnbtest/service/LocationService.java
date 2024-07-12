package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.payload.LocationDto;

import java.util.List;

public interface LocationService {

    LocationDto addLocation(LocationDto locationDto);

    void deleteLocation(long locationId);

    LocationDto getLocationById(long locationId);

    List<LocationDto> getAllLocation(int pageSize, int pageNo, String sortBy, String sortDir);

    LocationDto updateLocation(long locationId, LocationDto locationDto);
}
