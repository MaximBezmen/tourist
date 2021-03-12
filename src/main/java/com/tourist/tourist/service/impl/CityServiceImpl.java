package com.tourist.tourist.service.impl;

import com.tourist.tourist.entity.City;
import com.tourist.tourist.exeption.ResourceExist;
import com.tourist.tourist.exeption.ResourceNotFoundException;
import com.tourist.tourist.repo.CityRepository;
import com.tourist.tourist.service.CityService;
import com.tourist.tourist.service.dto.CityDto;
import com.tourist.tourist.service.mapper.CityMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityMapper cityMapper;
    public final CityRepository cityRepository;

    public CityServiceImpl(CityMapper cityMapper, CityRepository cityRepository) {
        this.cityMapper = cityMapper;
        this.cityRepository = cityRepository;
    }

    @Override
    public CityDto getCity(Long cityId) {
        City cityEntity = cityRepository.findById(cityId).orElseThrow(() ->
                new ResourceNotFoundException("Citi", "id", cityId));
        return cityMapper.toDto(cityEntity);
    }

    @Override
    public List<CityDto> getAllCity() {
        List<City> cityList = cityRepository.findAll();
        if (cityList.isEmpty() || cityList == null) {
            throw new ResourceNotFoundException("City not found");
        }
        return cityMapper.toDtos(cityList);
    }

    @Override
    public CityDto updateCity(CityDto cityDto) {
        City cityEntity = cityRepository.findById(cityDto.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Citi", "id", cityDto.getId()));
        if (cityDto.getNameCity() != null) {
            cityEntity.setNameCity(cityDto.getNameCity());

        }
        if (cityDto.getDescription() != null) {
            cityEntity.setDescription(cityEntity.getDescription());
        }

        return cityMapper.toDto(cityRepository.save(cityEntity));
    }

    @Override
    public void deleteCity(Long id) {
        City cityEntity = cityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Citi", "id", id));
        cityRepository.delete(cityEntity);
    }

    @Override
    public CityDto saveCity(CityDto cityDto) {
        City cityEntity = cityRepository.findByNameCityIgnoreCase(cityDto.getNameCity());
        if (cityEntity != null) {
            throw new ResourceExist(cityDto.getNameCity() + "already exists.");
        }
        cityEntity = cityMapper.toEntity(cityDto);
        return cityMapper.toDto(cityRepository.save(cityEntity));
    }

    @Override
    public City getByName(String cityName) {
        return cityRepository.findByNameCityIgnoreCase(cityName);
    }
}
