package com.test.Controller;
import com.test.dto.StatesDto.StateCreateDto;
import com.test.dto.StatesDto.StateDto;
import com.test.dto.StatesDto.StateFilterDto;
import com.test.dto.StatesDto.StateUpdateDto;

import com.test.mapper.StateMapper;
import com.test.service.StateService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/state")
public class StateController {


    private StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @Autowired
    private StateMapper stateMapper;

    @PostMapping("/created")
    public ResponseEntity<StateDto> createState(@RequestBody StateCreateDto dto) {
        StateDto state = stateService.createState(dto);
        return new ResponseEntity<>(state, HttpStatus.CREATED);
    }


    @DeleteMapping("/{stateId}")
    public void deleteState(@PathVariable Long stateId) {
        stateService.deleteState(stateId);
    }

    @GetMapping
    public List<StateDto> getAllStates(){
        List<StateDto> list = stateService.getAllStates();
        return list;
    }

    @GetMapping("/{stateId}")
    public StateDto getById(Long stateId){
        StateDto dto = stateService.getById(stateId);
        return dto;
    }

    @PutMapping("/state/{stateId}")
    public StateUpdateDto updateData(@PathVariable Long stateId , @RequestBody StateUpdateDto stateUpdateDto){
        return stateService.updateStateData(stateId,stateUpdateDto);
    }

    @PostMapping("/stateFilter")
    public Page<StateDto> searchStates(@RequestBody StateFilterDto stateFilterDto,
                                       @RequestParam( value = "page",defaultValue = "0", required = false) int page,
                                       @RequestParam(value = "pageSize",defaultValue = "5", required = false) int size) {
    return stateService.searchStates(stateFilterDto, page, size);
    }

    @GetMapping("/exportSheet")
    public void exportStatesToExcelFile(HttpServletResponse response) throws IOException {
        stateService.exportStatesToExcelFile(response);
    }

    @PostMapping(value = "/importCities", consumes = "multipart/form-data")
    public ResponseEntity<String> importStates(@RequestPart("file") MultipartFile file) {
        try {
            String response = stateService.importStatesToExcelFile(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed: " + e.getMessage());
        }
    }

}