package com.travelbnbtest.travelbnbtest.controller;

import com.travelbnbtest.travelbnbtest.payload.CountryDto;
import com.travelbnbtest.travelbnbtest.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto dto){
        CountryDto savedCountry = countryService.addCountry(dto);
        return new ResponseEntity<>(savedCountry,HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteCountry(@RequestParam long countryId){
        countryService.deleteCountry(countryId);
        return new ResponseEntity<>("Record is deleted !!",HttpStatus.OK);
    }
    @GetMapping("/id")
    public ResponseEntity<CountryDto> getCountryRecordById(@RequestParam long countryId){
        CountryDto getRecord = countryService.getCountryRecordById(countryId);
        return new ResponseEntity<>(getRecord,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountryRecord(
            @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(name="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir
    ){
        List<CountryDto> allCountryRecord = countryService.getAllCountryRecord(pageSize,pageNo,sortBy,sortDir);
        return new ResponseEntity<>(allCountryRecord,HttpStatus.OK);
    }
    @PutMapping("{countryId}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable long countryId,
                                                    @RequestBody CountryDto countryDto){
        CountryDto updated = countryService.updateCountry(countryId, countryDto);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }
}
