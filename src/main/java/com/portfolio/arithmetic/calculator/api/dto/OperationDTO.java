package com.portfolio.arithmetic.calculator.api.dto;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OperationDTO {

    private long id;

    private OperationType type;

    private BigDecimal cost;
}
