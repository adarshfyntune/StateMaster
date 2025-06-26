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
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
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


    @Override
    public void exportCitiesToExcelFile(HttpServletResponse response) throws IOException {
        List<City> cities = cityRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cities");
        String[] columns = {"City ID", "City Name", "State", "Created At", "Updated At", "Deleted At", "Status"};
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columns.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(columns[col]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (City city : cities) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(city.getCityId());
            row.createCell(1).setCellValue(city.getCityName());


            String stateName = city.getState() != null ? city.getState().getStateName() : "N/A";
            row.createCell(2).setCellValue(stateName);

            row.createCell(3).setCellValue(city.getCreatedAt() != null ? city.getCreatedAt().toString() : "");
            row.createCell(4).setCellValue(city.getUpdatedAt() != null ? city.getUpdatedAt().toString() : "");
            row.createCell(5).setCellValue(city.getDeletedAt() != null ? city.getDeletedAt().toString() : "");
            row.createCell(6).setCellValue(city.getStatus() != null ? city.getStatus().name() : "");
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=cities.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
