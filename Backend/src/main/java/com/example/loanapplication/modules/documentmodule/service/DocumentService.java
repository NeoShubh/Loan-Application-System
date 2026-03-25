package com.example.loanapplication.modules.documentmodule.service;

import com.example.loanapplication.modules.documentmodule.dto.DocumentRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.DocumentResponseDTO;

import java.util.List;

public interface DocumentService {

     DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO);

     DocumentResponseDTO getDocumentByID(String documentId);

     List<DocumentResponseDTO> getAllDocumentsByLoanId(String loanId);

     List<DocumentResponseDTO> getAllDocumentsByApplicantId(String applicantId);

     DocumentResponseDTO updateDocument(String documentId, DocumentRequestDTO documentRequestDTO);

     void deleteAllDocumentByLoanId(String loanId);

     void deleteDocumentById(String documentId);



}
