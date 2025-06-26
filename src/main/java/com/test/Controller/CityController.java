package com.test.Controller;
import com.test.dto.CititesDto.CityCreateDto;
import com.test.dto.CititesDto.CityDto;
import com.test.dto.CititesDto.CityFilterDto;
import com.test.dto.CititesDto.CityUpdateDto;
import com.test.service.CityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v2/city")
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @PostMapping("/createCity")
    public CityDto createCity(CityCreateDto cityCreateDto){
        CityDto cityDto = cityService.createCity(cityCreateDto);
        return cityDto;
    }

    @GetMapping("/{cityId}")
    public CityDto getById(@PathVariable Long cityId) {
        CityDto cityDto = cityService.getById(cityId);
        return cityDto;
    }


    @GetMapping
    public List<CityDto> getAllCities() {
        List<CityDto> list = cityService.getAllCities();
        return list;
    }

    @PutMapping("/updateCity/{cityId}")
    public CityUpdateDto updateCitydata(@PathVariable Long cityId, @RequestBody CityUpdateDto cityUpdateDto) {
        return cityService.updateCitydata(cityId, cityUpdateDto);
    }

    @DeleteMapping("/{cityId}")
    public void deleteCity(@PathVariable Long cityId){
        cityService.deleteCity(cityId);
    }


    @PostMapping("/filterCity")
    public Page<CityDto> getFilteredCities(@RequestBody CityFilterDto cityFilterDto,
                                           @RequestParam(value = "page",defaultValue = "0", required = false) int page,
                                           @RequestParam(value = "pageSize",defaultValue = "5", required = false) int size) {
        return cityService.getFilteredCities(cityFilterDto,page, size);
    }


    @GetMapping("/exportCitySheet")
    public void exportCitiesToExcelFile(HttpServletResponse response) throws IOException {
        cityService.exportCitiesToExcelFile(response);
    }
}
