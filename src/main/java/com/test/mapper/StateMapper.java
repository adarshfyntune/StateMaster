package com.test.mapper;

import com.test.dto.StatesDto.StateCreateDto;
import com.test.dto.StatesDto.StateDto;
import com.test.dto.StatesDto.StateUpdateDto;
import com.test.entity.State;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StateMapper {

    State toEntity(StateCreateDto dto);
    StateDto toDto(State state);
    List<StateDto> toDto(List<State> state);
    StateUpdateDto toUpdateDto(State state);

}
