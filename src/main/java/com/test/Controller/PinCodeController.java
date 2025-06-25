package com.test.Controller;

import com.test.dto.PinDtos.PinCodeCreateDto;
import com.test.dto.PinDtos.PinCodeDto;
import com.test.dto.PinDtos.PinCodeFilterDto;
import com.test.dto.PinDtos.PinCodeUpdateDto;
import com.test.mapper.PinCodeMapper;
import com.test.service.PinCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v3/pinCode")
public class PinCodeController {

    private PinCodeService pinCodeService;

    public PinCodeController(PinCodeService pinCodeService) {
        this.pinCodeService = pinCodeService;
    }

    @Autowired
    private PinCodeMapper pinCodeMapper;

    @PostMapping("/pinCodeCreated")
    public PinCodeDto pinCodeCreate(@RequestBody PinCodeCreateDto pinCodeCreateDto) {
        return pinCodeService.pinCodeCreate(pinCodeCreateDto);
    }

    @GetMapping("/getById/{pincodeId}")
    public PinCodeDto getById(Long pincodeId) {
        PinCodeDto pinCodeDto = pinCodeService.getById(pincodeId);
        return pinCodeDto;
    }

    @GetMapping
    public List<PinCodeDto> getAllPinCodes(){
        List<PinCodeDto> pinCodes = pinCodeService.getAllPinCodes();
        return pinCodes;
    }

    @PutMapping("/updatePinCode/{pincodeId}")
    public PinCodeUpdateDto updatePinCode(Long pincodeId, PinCodeUpdateDto pinCodeUpdateDto){
        return pinCodeService.updatePinCode(pincodeId,pinCodeUpdateDto);
    }

    @DeleteMapping("/{pincodeId}")
    public void deletePinCode(Long pincodeId) {
        pinCodeService.deletePinCode(pincodeId);
    }

    @PostMapping("/filter")
    public Page<PinCodeDto> getFilteredPinCodes(@RequestBody PinCodeFilterDto filterDto,
                                                @RequestParam( value = "page",defaultValue = "0", required = false) int page,
                                                @RequestParam(value = "pageSize",defaultValue = "5", required = false) int size) {
        return pinCodeService.getFilteredPinCodes(filterDto, page, size);
    }





}
