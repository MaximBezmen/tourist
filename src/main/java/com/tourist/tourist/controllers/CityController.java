package com.tourist.tourist.controllers;

import com.tourist.tourist.service.CityService;
import com.tourist.tourist.service.dto.CityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<CityDto> getCity(@PathVariable Long id) {
        return ResponseEntity.ok().body(cityService.getCity(id));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDto>> getAllCity() {
        return ResponseEntity.ok().body(cityService.getAllCity());
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<CityDto> updateCity(@RequestBody CityDto cityDto, @PathVariable Long id) {
        return ResponseEntity.ok().body(cityService.updateCity(cityDto, id));
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<CityDto> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cities")
    public ResponseEntity<CityDto> saveCity(@RequestBody CityDto cityDto) {
        return ResponseEntity.ok().body(cityService.saveCity(cityDto));
    }
}
