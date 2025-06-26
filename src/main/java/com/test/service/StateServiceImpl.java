package com.test.service;
import com.test.dto.StatesDto.StateCreateDto;
import com.test.dto.StatesDto.StateDto;
import com.test.dto.StatesDto.StateFilterDto;
import com.test.dto.StatesDto.StateUpdateDto;
import com.test.entity.State;
import com.test.mapper.StateMapper;
import com.test.respository.StateRepository;
import com.test.specification.AllSpecification;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
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

    @Override
    public Page<StateDto> searchStates(StateFilterDto stateFilterDto, int page, int size) {
        var stateSpec = AllSpecification.getStateSpecification(stateFilterDto);
        Page<State> statePage = stateRepository.findAll(stateSpec, PageRequest.of(page, size));

        return statePage.map(stateMapper::toDto);
    }

    @Override
    public void exportStatesToExcelFile(HttpServletResponse response) throws IOException {
        List<State> states = stateRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("States");

        String[] columns = {"State ID", "State Name", "Created At", "Updated At", "Deleted At", "Status"};
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
        for (State state : states) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(state.getStateId());
            row.createCell(1).setCellValue(state.getStateName());
            row.createCell(2).setCellValue(state.getCreatedAt() != null ? state.getCreatedAt().toString() : "");
            row.createCell(3).setCellValue(state.getUpdatedAt() != null ? state.getUpdatedAt().toString() : "");
            row.createCell(4).setCellValue(state.getDeletedAt() != null ? state.getDeletedAt().toString() : "");
            row.createCell(5).setCellValue(state.getStatus().name());
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=states.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }




}
