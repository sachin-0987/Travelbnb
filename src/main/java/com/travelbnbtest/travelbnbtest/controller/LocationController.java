package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.payload.LocationDto;
import com.travelbnbtest.travelbnbtest.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/addLocation")
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationDto locationDto){
        LocationDto savedLocation = locationService.addLocation(locationDto);
        return new ResponseEntity<>(savedLocation,HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteLocation(@RequestParam long locationId){
        locationService.deleteLocation(locationId);
        return new ResponseEntity<>("Record is deleted",HttpStatus.OK);
    }
    @GetMapping("/id")
    public ResponseEntity<LocationDto> getLocationById(@RequestParam long locationId){
        LocationDto locationById = locationService.getLocationById(locationId);
        return new ResponseEntity<>(locationById,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocation(
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir
    ){
        List<LocationDto> allLocation = locationService.getAllLocation(pageSize, pageNo, sortBy, sortDir);
        return new ResponseEntity<>(allLocation,HttpStatus.OK);
    }
    @PutMapping("{locationId}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable long locationId,
                                                      @RequestBody LocationDto  locationDto){
        LocationDto dto = locationService.updateLocation(locationId, locationDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
