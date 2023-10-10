package com.portfolio.arithmetic.calculator.infrastructure.client.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RandomClientRequestDTO {

    @Positive
    @Max(10000)
    private int num;

    @Positive
    @Max(32)
    private int len;

    @NotNull
    private BOOLEAN_PARAM digits;

    @NotNull
    private BOOLEAN_PARAM upperalpha;

    @NotNull
    private BOOLEAN_PARAM loweralpha;

    @NotNull
    private BOOLEAN_PARAM unique;

    public enum BOOLEAN_PARAM {
        on,
        off
    }
}
