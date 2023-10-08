package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.portfolio.arithmetic.calculator.api.dto.AuthRequestDTO;
import com.portfolio.arithmetic.calculator.core.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<String> generateToken(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        String token = authService.generateToken(authRequestDTO.getUsername(), authRequestDTO.getPassword());
        return ResponseEntity.ok(token);
    }

}
