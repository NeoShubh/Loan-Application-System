package com.example.loanapplication.modules.documentmodule.controller;

import com.example.loanapplication.modules.documentmodule.dto.DocumentRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.DocumentResponseDTO;
import com.example.loanapplication.modules.documentmodule.entity.Document;
import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import com.example.loanapplication.modules.documentmodule.service.DocumentService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;


    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/documents")
    ResponseEntity<DocumentResponseDTO> createDocument(@RequestParam MultipartFile file,@RequestParam UUID loanApplicationId,@RequestParam UUID applicantId,@RequestParam String documentType,@RequestParam UUID UploadedBy){
        DocumentResponseDTO documentResponseDTO = documentService.createDocument(file, loanApplicationId, applicantId, documentType, UploadedBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentResponseDTO);
    }

    @GetMapping("/documents/{documentId}")
    ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable UUID documentId) {
        DocumentResponseDTO documentResponseDTO = documentService.getDocumentById(documentId);
        return ResponseEntity.status(HttpStatus.FOUND).body(documentResponseDTO);
    }

    @PutMapping("/documents/{documentId}")
    ResponseEntity<DocumentResponseDTO> updateDocument(@PathVariable UUID documentId, @RequestBody DocumentRequestDTO documentRequestDTO) {
        DocumentResponseDTO documentResponseDTO = documentService.updateDocument(documentId, documentRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponseDTO);
    }

    @DeleteMapping("/loans/{loanId}/documents")
    ResponseEntity<String> deleteAllDocumentsByLoanId(@PathVariable UUID loanId) {
        documentService.deleteAllDocumentsByLoanId(loanId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Content Deleted");
    }

    @DeleteMapping("/loans/applicants/{applicantId}/documents")
    ResponseEntity<String> deleteAllDocumentsByApplicantId(@PathVariable UUID applicantId) {
        documentService.deleteAllDocumentsByApplicantId(applicantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Content Deleted");
    }

    @DeleteMapping("/documents/{documentId}")
    ResponseEntity<String> deleteDocumentsById(@PathVariable UUID documentId) {
        documentService.deleteDocumentsById(documentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Content Deleted");
    }

    @GetMapping("/loans/{loanId}/documents")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByLoanId(@PathVariable UUID loanId){
        List <DocumentResponseDTO> responesList = documentService.getAllDocumentsByLoanId(loanId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responesList);
    }

    @GetMapping("/loans/applicants/{applicantId}/documents")
    ResponseEntity<List<DocumentResponseDTO>> getAllDocumentsByApplicantId(@PathVariable UUID applicantId){
        List <DocumentResponseDTO> responselist = documentService.getAllDocumentsByApplicantId(applicantId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responselist);
    }

    @PutMapping("/documents/{documentId}/file")
    ResponseEntity<DocumentResponseDTO> updateDocumentFile(@PathVariable UUID documentId,@RequestParam MultipartFile file){
        DocumentResponseDTO documentResponseDTO = documentService.updateDocumentFile(documentId,file);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponseDTO);
    }

    @PutMapping("/documents/{documentId}/status")
    ResponseEntity<DocumentResponseDTO> updateDocumentStatus(UUID documentId,UUID verifiedBy,  DocumentStatus documentStatus, String Remarks){
        DocumentResponseDTO documentResponseDTO = documentService.updateDocumentStatus(documentId, verifiedBy,documentStatus,Remarks);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponseDTO);
    }



}
