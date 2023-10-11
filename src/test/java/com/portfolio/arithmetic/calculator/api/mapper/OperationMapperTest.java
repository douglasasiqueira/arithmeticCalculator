package com.portfolio.arithmetic.calculator.api.mapper;

import com.portfolio.arithmetic.calculator.api.dto.OperationDTO;
import com.portfolio.arithmetic.calculator.api.dto.RecordDTO;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

public class OperationMapperTest {
    @Test
    public void mapEntityToDTO() {
        Operation operation = new Operation();

        operation.setId(2L);
        operation.setCost(BigDecimal.TEN);
        operation.setType(OperationType.MULTIPLICATION);

        OperationMapper mapper = Mappers.getMapper(OperationMapper.class);
        OperationDTO operationDTO = mapper.operationToOperationDTO(operation);

        Assertions.assertEquals(operationDTO.getCost(), operation.getCost());
        Assertions.assertEquals(operationDTO.getId(), operation.getId());
        Assertions.assertEquals(operationDTO.getType(), operation.getType());
    }
}
