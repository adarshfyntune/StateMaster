package com.test.service;

import com.test.config.PinCodeSearchDto;
import com.test.dto.PinDtos.*;
import com.test.entity.State;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PinCodeService {

    public PinCodeDto pinCodeCreate(PinCodeCreateDto pinCodeCreateDto);

    public PinCodeDto getById(Long pincodeId);

    public List<PinCodeDto> getAllPinCodes();

    public PinCodeUpdateDto updatePinCode(Long pincodeId, PinCodeUpdateDto pinCodeUpdateDto);

    void deletePinCode(Long pincodeId);

    Page<PinCodeDto> getFilteredPinCodes(PinCodeFilterDto filterDto, int page, int size);

    public void exportPinCodesToExcelFiles(HttpServletResponse response)throws IOException;

   String importPinCodesToExcelFile(MultipartFile file) throws IOException;

    void saveItemProcess(PinCodeSearchDto dto);
}
