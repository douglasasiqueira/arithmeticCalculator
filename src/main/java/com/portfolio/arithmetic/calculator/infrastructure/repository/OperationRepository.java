package com.portfolio.arithmetic.calculator.infrastructure.repository;

import com.portfolio.arithmetic.calculator.core.entity.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {
    Optional<Operation> findByType(String type);

    @Override
    List<Operation> findAll();
}
