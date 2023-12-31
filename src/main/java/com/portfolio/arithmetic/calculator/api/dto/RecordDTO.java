package com.portfolio.arithmetic.calculator.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RecordDTO {

    private long id;

    private OperationDTO operationDTO;

    private long userId;

    private BigDecimal amount;

    private BigDecimal userBalance;

    private String operationResponse;

    private LocalDateTime date;
}
