package com.test.config;

import com.test.service.PinCodeService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelDataWriter implements ItemWriter<PinCodeSearchDto> {

    @Autowired
    private PinCodeService pinCodeService;
    @Override
    public void write(Chunk<? extends PinCodeSearchDto> chunk) throws Exception {

        for(PinCodeSearchDto pinCodeSearchDto:chunk){
            if (pinCodeSearchDto.getPinCode() == null) {
                // Optionally log and skip
                System.err.println("Skipped: PinCode is null for record - " + pinCodeSearchDto);
                continue;
            }
            pinCodeService.saveItemProcess(pinCodeSearchDto);
        }

    }
}