package com.test.service;

import com.test.dto.CititesDto.CityCreateDto;
import com.test.dto.CititesDto.CityDto;
import com.test.dto.CititesDto.CityFilterDto;
import com.test.dto.CititesDto.CityUpdateDto;
import com.test.entity.City;
import com.test.entity.State;
import com.test.mapper.CityMapper;
import com.test.respository.CityRepository;
import com.test.respository.StateRepository;
import com.test.specification.AllSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityMapper cityMapper;

    @Override
    public CityDto createCity(CityCreateDto cityCreateDto) {

        State state = stateRepository.findById(cityCreateDto.getStateId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + cityCreateDto.getStateId()));

        City entity = cityMapper.toEntity(cityCreateDto);
        entity.setState(state);
        City city =cityRepository.save(entity);
        return cityMapper.toDto(city);
    }

    @Override
    public CityDto getById(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("City Not Found with Id :"+cityId));
        return cityMapper.toDto(city);
    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.toDto(cities); // Converts List<City> to List<CityDto>
    }

    @Override
    public CityUpdateDto updateCitydata(Long cityId, CityUpdateDto cityUpdateDto) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("City Not found with id"+cityId));
        city.setCityName(cityUpdateDto.getCityName());

        City updateCity = cityRepository.save(city);
        return cityMapper.toUpdateDto(updateCity);

    }

    @Override
    public void deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
    }

    @Override
    public Page<CityDto> getFilteredCities(CityFilterDto cityFilterDto, int page, int size) {
        var citySpec = AllSpecification.getCitySpecification(cityFilterDto);
        Page<City> cityPage = cityRepository.findAll(citySpec, PageRequest.of(page,size));
        return cityPage.map(cityMapper::toDto);
    }
}
