package com.example.loanapplication.modules.rcumodule.repository;

import com.example.loanapplication.modules.rcumodule.entity.RCUCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RCUCaseRepository extends JpaRepository<RCUCase, UUID> {
}
