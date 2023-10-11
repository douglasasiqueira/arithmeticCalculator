package com.portfolio.arithmetic.calculator.api.mapper;

import com.portfolio.arithmetic.calculator.api.dto.OperationDTO;
import com.portfolio.arithmetic.calculator.api.dto.RecordDTO;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    OperationDTO operationToOperationDTO(Operation operation);
}
