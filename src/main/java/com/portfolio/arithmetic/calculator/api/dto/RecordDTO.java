package com.portfolio.arithmetic.calculator.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RecordDTO {

    private long id;

    private long operationId;

    private long userId;

    private BigDecimal amount;

    private BigDecimal userBalance;

    private String operationResponse;

    private LocalDateTime date;
}
