package com.portfolio.arithmetic.calculator.api.mapper;

import com.portfolio.arithmetic.calculator.api.dto.RecordDTO;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "operation.id", target = "operationId")
    @Mapping(source = "user.id", target = "userId")
    RecordDTO recordToRecordDTO(Record record);
}
