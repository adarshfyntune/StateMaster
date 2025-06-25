package com.test.service;

import com.test.dto.StatesDto.StateCreateDto;
import com.test.dto.StatesDto.StateDto;
import com.test.dto.StatesDto.StateFilterDto;
import com.test.dto.StatesDto.StateUpdateDto;
import org.springframework.data.domain.Page;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface StateService {
    public StateDto createState(StateCreateDto dto);
    void deleteState(Long stateId);
    StateDto getById(Long stateId);
    List<StateDto> getAllStates();
    StateUpdateDto updateStateData(Long stateId, StateUpdateDto stateUpdateDto);
    Page<StateDto> searchStates(StateFilterDto stateFilterDto, int page, int size);
    void exportStatesToExcel(HttpServletResponse response) throws IOException;




}
