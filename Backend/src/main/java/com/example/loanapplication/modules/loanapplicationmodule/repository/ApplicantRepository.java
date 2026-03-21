package com.example.loanapplication.modules.loanapplicationmodule.repository;

import com.example.loanapplication.modules.loanapplicationmodule.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
    Long deleteAllByLoanApplicationloanID(UUID loanID);
    List findByLoanApplicationloanID(UUID loanID);

}
