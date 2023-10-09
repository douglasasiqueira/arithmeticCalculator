package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import com.portfolio.arithmetic.calculator.infrastructure.client.RandomClient;
import com.portfolio.arithmetic.calculator.infrastructure.client.dto.RandomClientRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class RandomStringBehavior extends OperationBehavior{
    @Override
    public OperationType getOperationType(){
        return OperationType.RANDOM_STRING;
    };

    @Autowired
    private RandomClient randomClient;

    private RandomClientRequestDTO validateOperators(Map<String, String> operators) {
        final RandomClientRequestDTO randomClientRequestDTO = new RandomClientRequestDTO();

        if (Objects.isNull(operators)) {
            throw new IllegalArgumentException("Numeric operators must have numbers key.");
        }

        final String num = operators.getOrDefault("num", "10");
        final String len = operators.getOrDefault("len", "10");
        final String digits = operators.getOrDefault("digits", "on");
        final String upperalpha = operators.getOrDefault("upperalpha", "off");
        final String loweralpha = operators.getOrDefault("loweralpha", "off");
        final String unique = operators.getOrDefault("unique", "on");

        try {
            randomClientRequestDTO.setNum(Integer.parseInt(num));
            randomClientRequestDTO.setLen(Integer.parseInt(len));
            randomClientRequestDTO.setDigits(RandomClientRequestDTO.BOOLEAN_PARAM.valueOf(digits));
            randomClientRequestDTO.setUpperalpha(RandomClientRequestDTO.BOOLEAN_PARAM.valueOf(upperalpha));
            randomClientRequestDTO.setLoweralpha(RandomClientRequestDTO.BOOLEAN_PARAM.valueOf(loweralpha));
            randomClientRequestDTO.setUnique(RandomClientRequestDTO.BOOLEAN_PARAM.valueOf(unique));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Operators [num, len] must be integers.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    String.format("Operators [digits, upperalpha, loweralpha, unique] must have values %s.",
                            Arrays.asList(RandomClientRequestDTO.BOOLEAN_PARAM.values())));
        }

        return randomClientRequestDTO;
    };

    private Map<String, String> generateResult(RandomClientRequestDTO operators) {
        final Map<String, String> result = new HashMap<>();

        result.put("result", randomClient.getStrings(operators.getNum(),
                operators.getLen(),
                operators.getDigits().name(),
                operators.getUpperalpha().name(),
                operators.getLoweralpha().name(),
                operators.getUnique().name()).replace('\n', ','));

        return result;
    }

    public final Map<String, String> applyBehavior(Map<String, String> operators) {
        RandomClientRequestDTO sanitizedOperators = this.validateOperators(operators);
        return this.generateResult(sanitizedOperators);
    }
}
