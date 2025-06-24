package com.test.service;

import com.test.dto.CititesDto.CityCreateDto;
import com.test.dto.CititesDto.CityDto;
import com.test.dto.CititesDto.CityFilterDto;
import com.test.dto.CititesDto.CityUpdateDto;
import com.test.dto.PinDtos.PinCodeDto;
import com.test.dto.PinDtos.PinCodeFilterDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CityService {


     public CityDto createCity(CityCreateDto cityCreateDto);

     public CityDto getById(Long cityId);

     public List<CityDto> getAllCities();

     public CityUpdateDto updateCitydata(Long cityId, CityUpdateDto cityUpdateDto);

     public void deleteCity(Long cityId);

     Page<CityDto> getFilteredCities(CityFilterDto cityFilterDto, int page, int size);



}
