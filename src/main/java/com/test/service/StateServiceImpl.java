package com.test.service;

import com.test.dto.CititesDto.CityDto;
import com.test.dto.CititesDto.CityFilterDto;
import com.test.dto.StatesDto.StateCreateDto;
import com.test.dto.StatesDto.StateDto;
import com.test.dto.StatesDto.StateFilterDto;
import com.test.dto.StatesDto.StateUpdateDto;
import com.test.entity.City;
import com.test.entity.State;
import com.test.mapper.StateMapper;
import com.test.respository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateMapper stateMapper;

    @Override
    public StateDto createState(StateCreateDto dto) {
        State entity = stateMapper.toEntity(dto);
        State state= stateRepository.save(entity);
        return stateMapper.toDto(state);
    }

    @Override
    public void deleteState(Long stateId) {
        stateRepository.deleteById(stateId);
    }


    @Override
    public List<StateDto> getAllStates() {

        List<State> states = stateRepository.findAll();

        return stateMapper.toDto(states);

    }

    @Override
    public StateDto getById(Long stateId) {
        State state = stateRepository.findBystateIdAndDeletedAtIsNull(stateId);

    if(state == null)
    {
        throw  new IllegalArgumentException("ID not found");

    }
            return stateMapper.toDto(state);
    }


    @Override
    public StateUpdateDto updateStateData(Long stateId, StateUpdateDto stateUpdateDto) {
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new IllegalArgumentException("State not found with id:"+stateId));
        state.setStateName(stateUpdateDto.getStateName());

        State updateState = stateRepository.save(state);
        return stateMapper.toUpdateDto(updateState);
    }




}
