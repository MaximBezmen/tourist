package com.tourist.tourist.repo;

import com.tourist.tourist.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByNameCityIgnoreCase(String cityName);
}
