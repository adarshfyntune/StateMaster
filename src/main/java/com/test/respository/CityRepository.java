package com.test.respository;

import com.test.entity.City;
import com.test.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    City findByCityIdAndDeletedAtNull(Long cityId);
    Optional<City> findByCityNameIgnoreCase(String cityName);

}
