package com.example.loanapplication.modules.rcumodule.repository;

import com.example.loanapplication.modules.rcumodule.entity.RCUCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RCUCaseRepository extends JpaRepository<RCUCase, UUID> {
}
