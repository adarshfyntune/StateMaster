package com.test.mapper;
import com.test.dto.PinDtos.PinCodeCreateDto;
import com.test.dto.PinDtos.PinCodeDto;
import com.test.dto.PinDtos.PinCodeUpdateDto;
import com.test.entity.PinCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PinCodeMapper {

    PinCode dtoToEntity(PinCodeCreateDto pinCodeCreateDto);
    @Mapping(target = "cityId", source = "city.cityId")
    PinCodeDto entityToDto(PinCode pinCode);
    List<PinCodeDto> entityToDtoList(List<PinCode> pinCode);
    PinCodeUpdateDto toUpdatePinCode(PinCode pinCode);

}
