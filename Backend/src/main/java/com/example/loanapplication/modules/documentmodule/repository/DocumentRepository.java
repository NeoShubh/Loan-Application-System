package com.example.loanapplication.modules.documentmodule.repository;

//import com.example.loanapplication.modules.documentmodule.entity.Document;
import com.example.loanapplication.modules.documentmodule.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findAllByLoanApplication_LoanID(UUID loanID);
    List<Document> findAllByApplicant_applicantId(UUID applicantId);
    Long deleteAllByLoanApplicationLoanID(UUID loanID);
    Long deleteAllByApplicantApplicantId(UUID applicantId);
}
