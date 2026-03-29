package com.example.loanapplication.modules.rcumodule.repository;

import com.example.loanapplication.modules.rcumodule.entity.RCUCase;
import com.example.loanapplication.modules.rcumodule.enums.RCUStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RCUCaseRepository extends JpaRepository<RCUCase, UUID> {
    boolean existsByLoan_LoanIDAndRcuStatus(UUID loanId, RCUStatus rcuStatus);}
