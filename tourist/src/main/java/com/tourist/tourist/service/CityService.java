package com.tourist.tourist.service;

import com.tourist.tourist.entity.City;
import com.tourist.tourist.service.dto.CityDto;

import java.util.List;

public interface CityService {
    CityDto getCity(Long id);

    List<CityDto> getAllCity();

    CityDto updateCity(CityDto cityDto);

    void deleteCity(Long id);

    CityDto saveCity(CityDto cityDto);

    City getByName (String cityName);
}
