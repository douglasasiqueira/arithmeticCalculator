package com.portfolio.arithmetic.calculator.api.mapper;

import com.portfolio.arithmetic.calculator.api.dto.UserDTO;
import com.portfolio.arithmetic.calculator.core.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class UserMapperTest {
    @Test
    public void mapDTOToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("user password");
        userDTO.setUsername("user username");

        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        User user = mapper.userDTOToUser(userDTO);

        Assertions.assertEquals(userDTO.getPassword(), user.getPassword());
        Assertions.assertEquals(userDTO.getUsername(), user.getUsername());
    }
}
