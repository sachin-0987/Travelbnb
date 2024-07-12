package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.payload.PropertyDto;
import com.travelbnbtest.travelbnbtest.repository.PropertyRepository;
import com.travelbnbtest.travelbnbtest.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyRepository propertyRepository;
    private PropertyService propertyService;

    public PropertyController(PropertyRepository propertyRepository, PropertyService propertyService) {
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;

    }

    @GetMapping("/search/properties")
    public ResponseEntity<List<Property>> searchProperty(@RequestParam String name) {
        List<Property> properties = propertyRepository.searchProperty(name);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @PostMapping("/addProperty")
    public ResponseEntity<PropertyDto> addProperty(@RequestParam long locationId,
                                                   @RequestParam long countryId,
                                                   @RequestBody PropertyDto dto){
        PropertyDto dto1 = propertyService.addProperty(locationId, countryId, dto);
        return new ResponseEntity<>(dto1,HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProperty(@RequestParam long propertyId){
        propertyService.deleteProperty(propertyId);
        return new ResponseEntity<>("Record is deleted",HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<PropertyDto> getPropertyById(@RequestParam long propertyId){
        PropertyDto propertyById = propertyService.getPropertyById(propertyId);
        return new ResponseEntity<>(propertyById,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperty(
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir
    ){
        List<PropertyDto> allProperty = propertyService.getAllProperty(pageSize, pageNo, sortBy, sortDir);
        return new ResponseEntity<>(allProperty,HttpStatus.OK);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<PropertyDto> updateProperty(@PathVariable long propertyId,
                                                   @RequestParam long locationId,
                                                   @RequestParam long countryId,
                                                      @RequestBody PropertyDto propertyDto
                                                    ){
        PropertyDto dto = propertyService.updateProperty(propertyId, locationId, countryId, propertyDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}