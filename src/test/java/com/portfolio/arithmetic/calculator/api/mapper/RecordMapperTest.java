package com.portfolio.arithmetic.calculator.api.mapper;

import com.portfolio.arithmetic.calculator.api.dto.RecordDTO;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import com.portfolio.arithmetic.calculator.core.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

public class RecordMapperTest {
    @Test
    public void mapEntityToDTO() {
        User user = new User();
        user.setId(1L);

        Operation operation = new Operation();
        operation.setId(2L);

        Record record = new Record();
        record.setUser(user);
        record.setOperation(operation);
        record.setAmount(BigDecimal.TEN);

        RecordMapper mapper = Mappers.getMapper(RecordMapper.class);
        RecordDTO recordDTO = mapper.recordToRecordDTO(record);

        Assertions.assertEquals(recordDTO.getOperationId(), operation.getId());
        Assertions.assertEquals(recordDTO.getUserId(), user.getId());
        Assertions.assertEquals(recordDTO.getAmount(), record.getAmount());
    }
}
