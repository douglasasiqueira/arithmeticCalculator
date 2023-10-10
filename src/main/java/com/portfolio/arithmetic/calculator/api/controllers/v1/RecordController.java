package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.portfolio.arithmetic.calculator.api.mapper.RecordMapper;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import com.portfolio.arithmetic.calculator.core.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    private final RecordMapper recordMapper;

    @GetMapping
    public ResponseEntity<?> getAllRecords(@RequestParam(required = false) Long operationId,
                                           @RequestParam int page,
                                           @RequestParam int size) {
        final List<Record> recordList = recordService.findAllByUserIdAndOperationIdPageable(operationId, page, size);
        final Long countRecords = recordService.countAllByUserIdAndOperationId(operationId);

        return ResponseEntity.ok()
                .header("X-total-count", countRecords.toString())
                .body(recordList.stream()
                .map(recordMapper::recordToRecordDTO)
                .collect(Collectors.toList()));
    }

}
