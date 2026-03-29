package com.example.loanapplication.modules.rcumodule.service;

import com.example.loanapplication.modules.documentmodule.dto.DocumentStatusDTO.DocumentStatusRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.WholeDocuementDTO.DocumentResponseDTO;
import com.example.loanapplication.modules.rcumodule.dto.standardDTOs.RCUCaseResponseDTO;
import com.example.loanapplication.modules.rcumodule.enums.RCUStatus;
import com.example.loanapplication.modules.rcumodule.repository.RCUCaseRepository;

import javax.print.Doc;
import java.util.List;
import java.util.UUID;

public interface RCUService {

    RCUCaseResponseDTO CreateRCUCase(UUID loanId);
    void DeleteRCUCaseById(UUID rcuCaseId);
    DocumentResponseDTO updateDocumentStatusAndRemarks(String documentId, DocumentStatusRequestDTO documentStatusRequestDTO);
    DocumentResponseDTO getDocument(String documentId);
    List<DocumentResponseDTO> getAllDocumentByApplicant(String applicantId);
    List<DocumentResponseDTO> getAllDOcumentByLoanId(String loanId);
    RCUCaseResponseDTO updateRCUCaseStatus(UUID rcuCaseId, RCUStatus rcuStatus);
    RCUCaseResponseDTO AssignedRCUCase(UUID rcuCaseId,UUID assignedUser);
    void RCUCaseDecisionMaking(UUID rcuCaseId);
    boolean CheckRCUCaseExistsForLoanId(UUID loanId, RCUStatus rcuStatus);
}
