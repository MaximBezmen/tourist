package com.tourist.tourist.service.mapper;

import com.tourist.tourist.entity.City;
import com.tourist.tourist.service.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CityMapper {

    CityDto toDto(City entity);

    List<CityDto> toDtos(List<City> entity);

    City toEntity(CityDto dto);
}
