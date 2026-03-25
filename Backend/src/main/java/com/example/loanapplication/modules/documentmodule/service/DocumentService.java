package com.example.loanapplication.modules.documentmodule.service;

import com.example.loanapplication.modules.documentmodule.dto.DocumentRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.DocumentResponseDTO;
import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface DocumentService {

     DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO, MultipartFile file);

     DocumentResponseDTO getDocumentById(UUID documentId);

     List<DocumentResponseDTO> getAllDocumentsByLoanId(UUID loanId);

     List<DocumentResponseDTO> getAllDocumentsByApplicantId(UUID applicantId);

     DocumentResponseDTO updateDocument(UUID documentId, DocumentRequestDTO documentRequestDTO);

     DocumentResponseDTO updateDocumentFile(UUID documentId, MultipartFile file);

     DocumentResponseDTO updateDocumentStatus(UUID documentId, DocumentStatus documentStatus, String Remarks);

     void deleteAllDocumentsByLoanId(UUID loanId);

     void deleteDocumentsById(UUID documentId);
}
