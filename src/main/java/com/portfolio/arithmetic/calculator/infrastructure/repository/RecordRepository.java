package com.portfolio.arithmetic.calculator.infrastructure.repository;

import com.portfolio.arithmetic.calculator.core.entity.Record;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends CrudRepository<Record, Long> {
    @Query("FROM Record r WHERE r.user.id = :userId AND " +
            "(:operationId is NULL OR r.operation.id = :operationId)")
    List<Record> findAllByUserIdAndOperationIdPageableAndOperationIdPageable(@Param("userId") Long userId,
                                          @Param("operationId") Long operationId,
                                          Pageable pageable);

    @Query("SELECT COUNT(r) FROM Record r WHERE r.user.id = :userId AND " +
            "(:operationId is NULL OR r.operation.id = :operationId)")
    long countByUserIdAndOperationId(Long userId, Long operationId);

    Record findByIdAndUserId(Long Id, Long UserId);
}
