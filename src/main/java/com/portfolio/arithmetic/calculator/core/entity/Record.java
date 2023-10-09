package com.portfolio.arithmetic.calculator.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operation_id")
    private Operation operation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal amount;

    @Column(name = "user_balance")
    private BigDecimal userBalance;

    @Column(name = "operation_response")
    private String operationResponse;

    @CreationTimestamp
    private LocalDateTime date;
}
