package com.test.service;

import com.test.dto.CititesDto.CityCreateDto;
import com.test.dto.CititesDto.CityDto;
import com.test.dto.CititesDto.CityFilterDto;
import com.test.dto.CititesDto.CityUpdateDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface CityService {


     public CityDto createCity(CityCreateDto cityCreateDto);

     public CityDto getById(Long cityId);

     public List<CityDto> getAllCities();

     public CityUpdateDto updateCitydata(Long cityId, CityUpdateDto cityUpdateDto);

     public void deleteCity(Long cityId);

     Page<CityDto> getFilteredCities(CityFilterDto cityFilterDto, int page, int size);

     void exportCitiesToExcelFile(HttpServletResponse response) throws IOException;

     String saveExcelImport(MultipartFile file) throws IOException;

}
