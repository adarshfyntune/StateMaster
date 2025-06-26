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
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public void exportPinCodesToExcelFiles(HttpServletResponse response) throws IOException {
        List<PinCode> pinCodes = pinCodeRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pincodes");

        String[] columns = {"PinCode Id", "Pin Codes", "City Name", "State Names"};

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columns.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(columns[col]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (PinCode pinCode : pinCodes) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(pinCode.getPincodeId());
            row.createCell(1).setCellValue(pinCode.getPincode());

            String cityName = pinCode.getCity() != null ? pinCode.getCity().getCityName() : "N/A";
            row.createCell(2).setCellValue(cityName);

            String stateName = (pinCode.getCity() != null && pinCode.getCity().getState() != null)
                    ? pinCode.getCity().getState().getStateName()
                    : "N/A";
            row.createCell(3).setCellValue(stateName);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=pincodes.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }




}
