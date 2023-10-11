package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.portfolio.arithmetic.calculator.api.mapper.OperationMapper;
import com.portfolio.arithmetic.calculator.core.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/operation")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    private final OperationMapper operationMapper;

    @PostMapping("/{id}")
    public ResponseEntity<?> executeOperation(@PathVariable int id,
                                              @RequestBody Map<String, String> operation) {
        return ResponseEntity.ok(operationService.executeOperation(id, operation));
    }

    @GetMapping
    public ResponseEntity<?> getAllOperation() {
        return ResponseEntity.ok(operationService.getAll().stream()
                .map(operationMapper::operationToOperationDTO)
                .collect(Collectors.toList()));
    }

}
