package com.test.service;

import com.test.config.PinCodeSearchDto;
import com.test.dto.PinDtos.*;
import com.test.entity.City;
import com.test.entity.PinCode;
import com.test.entity.State;
import com.test.mapper.PinCodeMapper;
import com.test.respository.CityRepository;
import com.test.respository.PinCodeRepository;
import com.test.respository.StateRepository;
import com.test.specification.AllSpecification;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PinCodeServiceImpl implements PinCodeService {

    @Autowired
    private PinCodeRepository pinCodeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

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
        if (pinCode == null) {
            throw new IllegalArgumentException("ID not found");
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
                .orElseThrow(() -> new IllegalArgumentException("PinCode Not Found with id" + pincodeId));
        pinCode.setPinCode(pinCodeUpdateDto.getPincode());
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
            row.createCell(1).setCellValue(pinCode.getPinCode());

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

    @Override
    public String importPinCodesToExcelFile(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        sheet.forEach(row -> {
            PinCode pinCode = new PinCode();
            pinCode.setPinCode(Long.valueOf(row.getCell(1).getStringCellValue()));
            String cityName = row.getCell(2).getStringCellValue();
            String state = row.getCell(3).getStringCellValue();
            City city = cityRepository.findByCityNameIgnoreCase(cityName).orElseThrow(() ->
                    new IllegalArgumentException("City Name Not Found"));
            pinCodeRepository.save(pinCode);

        });
        return "Data Inserted Sucessfully";
    }


    @Override
    public void saveItemProcess(PinCodeSearchDto dto) {
        // 1. Get or create the State
        State state = stateRepository.findByStateNameIgnoreCase(dto.getStateName())
                .orElseGet(() -> {
                    State newState = new State();
                    newState.setStateName(dto.getStateName());
                    return stateRepository.save(newState);
                });

        // 2. Get or create the City (make sure State is assigned if required)
        City city = cityRepository.findByCityNameIgnoreCase(dto.getCityName())
                .orElseGet(() -> {
                    City newCity = new City();
                    newCity.setCityName(dto.getCityName());
                    newCity.setState(state); // ✅ Link city to state
                    return cityRepository.save(newCity);
                });

        // 3. Get or create the PinCode (link city)
        pinCodeRepository.findByPinCode(dto.getPinCode())
                .orElseGet(() -> {
                    PinCode p = new PinCode();
                    if (dto.getPinCode() == null) {
                        // Handle the missing pin code:
                        // 1. Log the error and skip saving
                        // 2. Throw an exception to stop processing
                        // 3. Or provide a default value if that makes sense
                        throw new IllegalArgumentException("Pin code is required but was null or empty");
                    }
                    p.setPinCode(dto.getPinCode());
                    p.setCity(city); // ✅ Link pincode to city
                    return pinCodeRepository.save(p);
                });
    }



}