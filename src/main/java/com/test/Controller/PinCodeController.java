package com.test.Controller;

import com.test.dto.PinDtos.PinCodeCreateDto;
import com.test.dto.PinDtos.PinCodeDto;
import com.test.dto.PinDtos.PinCodeFilterDto;
import com.test.dto.PinDtos.PinCodeUpdateDto;
import com.test.mapper.PinCodeMapper;
import com.test.service.PinCodeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job pinCodeJob;


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


    @GetMapping("/exportPinCodeFile")
    public void exportPinCodesToExcel(HttpServletResponse response) throws IOException {
        pinCodeService.exportPinCodesToExcelFiles(response);
    }

    @PostMapping(value = "/pinCodes/bulkupload" ,consumes = "multipart/form-data")
    public String importPinCodesToExcelFile(MultipartFile file) throws IOException{
        String data = pinCodeService.importPinCodesToExcelFile(file);
        return "Data imported Sucessfully";
    }

    @PostMapping(value = "/batch/import",consumes = "multipart/form-data" )
    public String importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        File tempFile = File.createTempFile("product", ".xlsx");
        file.transferTo(tempFile);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filePath", tempFile.getAbsolutePath())
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(pinCodeJob, jobParameters);
        return "Batch Job Started!";

    }

}
