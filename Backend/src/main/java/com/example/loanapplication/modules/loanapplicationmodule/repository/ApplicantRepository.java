package com.example.loanapplication.modules.loanapplicationmodule.repository;

import com.example.loanapplication.modules.loanapplicationmodule.entity.Applicant;
import com.example.loanapplication.modules.loanapplicationmodule.enums.ApplicantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
    Long deleteAllByLoanApplicationLoanID(UUID loanID);
    List <Applicant> findByLoanApplicationLoanID(UUID loanID);
//    Optional<Applicant> findByLoanApplication_LoanIdAndApplicantType(UUID loanId, ApplicantType type);
//    List<Applicant> findAllByLoanApplication_LoanIdAndApplicantType(UUID loanId, ApplicantType type);
    Applicant findByLoanApplication_LoanIDAndApplicantType(UUID loanID, ApplicantType applicantType);
    List<Applicant> findAllByLoanApplication_LoanIDAndApplicantType(UUID loanID, ApplicantType applicantType);
}
