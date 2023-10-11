package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.arithmetic.calculator.api.dto.RecordDTO;
import com.portfolio.arithmetic.calculator.api.mapper.RecordMapper;
import com.portfolio.arithmetic.calculator.configuration.security.filter.AuthenticationFilterJWT;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.core.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecordController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecordControllerTest {

    @MockBean
    private AuthenticationFilterJWT authenticationFilterJWT;

    @MockBean
    private RecordService recordService;

    @MockBean
    private RecordMapper recordMapper;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnErrorIfInvalidPayload() throws Exception {
        mockMvc.perform(get("/v1/record"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/v1/record?page=1"))
                .andExpect(status().isBadRequest());

        verify(recordService, times(0))
                .findAllByUserIdAndOperationIdPageable(anyLong(), anyInt(), anyInt());

        mockMvc.perform(get("/v1/record?page=1&size=10"))
                .andExpect(status().isOk());

        verify(recordService, times(1))
                .findAllByUserIdAndOperationIdPageable(null, 1, 10);

        mockMvc.perform(get("/v1/record?page=1&size=10&operationId=1234"))
                .andExpect(status().isOk());

        verify(recordService, times(1))
                .findAllByUserIdAndOperationIdPageable(1234L, 1, 10);
    }

    @Test
    public void shouldTranslateResponseCorrectly() throws Exception {
        final List<Record> recordList = records();
        final List<RecordDTO> recordDTOList = recordList.stream()
                .map(recordMapper::recordToRecordDTO).toList();

        when(recordService.findAllByUserIdAndOperationIdPageable(anyLong(), anyInt(), anyInt()))
                .thenReturn(recordList);
        when(recordService.countAllByUserIdAndOperationId(anyLong()))
                .thenReturn(10L);

        mockMvc.perform(get("/v1/record?page=1&size=10&operationId=1234"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-total-count", "10"))
                .andExpect(content().string(objectMapper.writeValueAsString(recordDTOList)));
    }

    private List<Record> records() {
        final User user = new User();
        user.setId(12L);

        final Operation operation = new Operation();
        operation.setId(1L);

        final Record record = new Record();
        record.setOperation(operation);
        record.setUser(user);
        record.setAmount(BigDecimal.TEN);
        record.setOperationResponse("operation response");
        record.setUserBalance(BigDecimal.ONE);
        record.setDate(LocalDateTime.now());

        return List.of(record);
    }

}
