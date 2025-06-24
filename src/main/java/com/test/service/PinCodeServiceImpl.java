package com.test.service;

import com.test.dto.PinDtos.PinCodeCreateDto;
import com.test.dto.PinDtos.PinCodeDto;
import com.test.dto.PinDtos.PinCodeFilterDto;
import com.test.dto.PinDtos.PinCodeUpdateDto;
import com.test.entity.City;
import com.test.entity.PinCode;
import com.test.mapper.PinCodeMapper;
import com.test.respository.CityRepository;
import com.test.respository.PinCodeRepository;
import com.test.specification.AllSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PinCodeServiceImpl implements PinCodeService {

    @Autowired
    private PinCodeRepository pinCodeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PinCodeMapper pinCodeMapper;

    @Override
    public PinCodeDto pinCodeCreate(PinCodeCreateDto pinCodeCreateDto) {

        City city = cityRepository.findById(pinCodeCreateDto.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("City not found with provided id "));


        PinCode pinCode = pinCodeMapper.dtoToEntity(pinCodeCreateDto);
        pinCode.setCity(city);
        PinCode pinCode1 = pinCodeRepository.save(pinCode);
        return pinCodeMapper.entityToDto(pinCode1);
    }

    @Override
    public PinCodeDto getById(Long pincodeId) {
        PinCode pinCode = pinCodeRepository.getReferenceById(pincodeId);
        if(pinCode == null)
        {
            throw  new IllegalArgumentException("ID not found");
        }
        return pinCodeMapper.entityToDto(pinCode);
    }

    @Override
    public List<PinCodeDto> getAllPinCodes() {
        List<PinCode> pinCodes = pinCodeRepository.findAll();
        return pinCodeMapper.entityToDtoList(pinCodes);
    }

    @Override
    public PinCodeUpdateDto updatePinCode(Long pincodeId, PinCodeUpdateDto pinCodeUpdateDto) {
        PinCode pinCode = pinCodeRepository.findById(pincodeId)
                .orElseThrow(()-> new IllegalArgumentException("PinCode Not Found with id"+pincodeId));
        pinCode.setPincode(pinCodeUpdateDto.getPincode());
        pinCode = pinCodeRepository.save(pinCode);

        return pinCodeMapper.toUpdatePinCode(pinCode);
    }

    @Override
    public void deletePinCode(Long pincodeId) {
        pinCodeRepository.deleteById(pincodeId);
    }

    @Override
    public Page<PinCodeDto> getFilteredPinCodes(PinCodeFilterDto filterDto, int page, int size) {
        var spec = AllSpecification.getPinSpecification(filterDto);
        Page<PinCode> pinCodePage = pinCodeRepository.findAll(spec, PageRequest.of(page, size));
        return pinCodePage.map(pinCodeMapper::entityToDto);
    }


}
