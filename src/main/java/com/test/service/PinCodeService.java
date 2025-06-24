package com.test.service;

import com.test.dto.PinDtos.PinCodeCreateDto;
import com.test.dto.PinDtos.PinCodeDto;
import com.test.dto.PinDtos.PinCodeFilterDto;
import com.test.dto.PinDtos.PinCodeUpdateDto;
import com.test.dto.StatesDto.StateDto;
import com.test.dto.StatesDto.StateFilterDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PinCodeService {

    public PinCodeDto pinCodeCreate(PinCodeCreateDto pinCodeCreateDto);

    public PinCodeDto getById(Long pincodeId);

    public List<PinCodeDto> getAllPinCodes();

    public PinCodeUpdateDto updatePinCode(Long pincodeId, PinCodeUpdateDto pinCodeUpdateDto);

    void deletePinCode(Long pincodeId);

    Page<PinCodeDto> getFilteredPinCodes(PinCodeFilterDto filterDto, int page, int size);
}
