package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.Location;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.LocationDto;
import com.travelbnbtest.travelbnbtest.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService{

    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public LocationDto addLocation(LocationDto locationDto) {
        Location location = dtoToEntity(locationDto);

        Location save = locationRepository.save(location);
        LocationDto dto = entityToDto(save);
        return dto;
    }

    @Override
    public void deleteLocation(long locationId) {
        locationRepository.deleteById(locationId);
    }

    @Override
    public LocationDto getLocationById(long locationId) {
        Optional<Location> byId = locationRepository.findById(locationId);
        if (byId.isPresent()){
            Location location = byId.get();
            LocationDto dto = entityToDto(location);
            return dto;
        }else {
            throw new ResourceNotFoundException("Location not exist with id :" +locationId);
        }
    }

    @Override
    public List<LocationDto> getAllLocation(int pageSize, int pageNo, String sortBy, String sortDir) {
        Pageable pageable=null;
        if (sortDir.equalsIgnoreCase("asc")){
            pageable= PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        }else if (sortDir.equalsIgnoreCase("desc")){
            pageable=PageRequest.of(pageNo,pageSize,Sort.by(sortBy).descending());
        } else {
            pageable=PageRequest.of(pageNo,pageSize);
        }

        Page<Location> all = locationRepository.findAll(pageable);
        List<Location> content = all.getContent();
        List<LocationDto> collect = content.stream().map(c -> entityToDto(c)).collect(Collectors.toList());
        return collect;

    }

    @Override
    public LocationDto updateLocation(long locationId, LocationDto locationDto) {
        Location location=null;
        Optional<Location> byId = locationRepository.findById(locationId);
        if (byId.isPresent()){
         location  = byId.get();
         location = dtoToEntity(locationDto);
         location.setId(locationId);

            Location save = locationRepository.save(location);
            LocationDto dto = entityToDto(save);
            return dto;
        }else {
            throw new ResourceNotFoundException("Location not found with id: "+locationId);
        }
    }

    //dto to entity
   Location dtoToEntity(LocationDto locationDto){
        Location entity=new Location();
        entity.setName(locationDto.getName());
        return entity;
    }
// entity to dto

    LocationDto entityToDto(Location location){
        LocationDto dto=new LocationDto();
        dto.setId(location.getId());
        dto.setName(location.getName());
        return dto;
    }
}
