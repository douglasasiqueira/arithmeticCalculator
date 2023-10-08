package com.portfolio.arithmetic.calculator.core.entity;

import com.portfolio.arithmetic.calculator.core.customException.InsufficientBalanceException;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
@Data
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String status;

    private BigDecimal balance;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public List<GrantedAuthority> getAuthorities() {
        return Stream.of("default")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean hasEnoughBalance(final BigDecimal value) {
        return this.getBalance().compareTo(value) > -1;
    }

    public void withdraw(final BigDecimal value) {
        if (!hasEnoughBalance(value)) {
            throw new InsufficientBalanceException("Not enough balance");
        }

        this.setBalance(this.getBalance().subtract(value));
    }

    @Override
    public String toString(){
        return String.format("(User UserID = %s, Status = %s, Balance = %s)",
                this.id, this.status, this.balance);
    }
}
