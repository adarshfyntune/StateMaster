package com.test.respository;

import com.test.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    City findByCityIdAndDeletedAtNull(Long cityId);
}
