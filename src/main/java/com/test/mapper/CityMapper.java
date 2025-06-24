package com.test.mapper;

import com.test.dto.CititesDto.CityCreateDto;
import com.test.dto.CititesDto.CityDto;
import com.test.dto.CititesDto.CityUpdateDto;
import com.test.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityMapper {

   City toEntity(CityCreateDto cityCreateDto);
   CityDto toDto(City city);
   List<CityDto> toDto(List<City> cities);
   CityUpdateDto toUpdateDto(City City);



}
