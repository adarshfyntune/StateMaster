package com.test.service;

import com.test.dto.CititesDto.CityDto;
import com.test.dto.CititesDto.CityFilterDto;
import com.test.dto.StatesDto.StateCreateDto;
import com.test.dto.StatesDto.StateDto;
import com.test.dto.StatesDto.StateFilterDto;
import com.test.dto.StatesDto.StateUpdateDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface StateService {
    public StateDto createState(StateCreateDto dto);
    void deleteState(Long stateId);
    StateDto getById(Long stateId);
    List<StateDto> getAllStates();
    StateUpdateDto updateStateData(Long stateId, StateUpdateDto stateUpdateDto);
//    public Page<StateDto> searchStates(StateFilterDto stateFilterDto);




}
