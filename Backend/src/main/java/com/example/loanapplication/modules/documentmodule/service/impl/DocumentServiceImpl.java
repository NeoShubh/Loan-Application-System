package com.example.loanapplication.modules.documentmodule.service.impl;

import com.example.loanapplication.exception.document.DocumentFormatNotAllowedException;
import com.example.loanapplication.exception.document.DocumentNotFoundException;
import com.example.loanapplication.modules.documentmodule.dto.DocumentRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.DocumentResponseDTO;
import com.example.loanapplication.modules.documentmodule.entity.Document;
import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import com.example.loanapplication.modules.documentmodule.enums.DocumentType;
import com.example.loanapplication.modules.documentmodule.repository.DocumentRepository;
import com.example.loanapplication.modules.documentmodule.service.DocumentService;
import com.example.loanapplication.modules.documentmodule.service.FileStorageService;
import com.example.loanapplication.modules.loanapplicationmodule.entity.Applicant;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.usermodule.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    public DocumentServiceImpl(DocumentRepository documentRepository, FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO, MultipartFile file) {

        LoanApplication loanApplication = LoanApplication.builder().loanID(UUID.fromString(documentRequestDTO.getLoanApplication())).build();
        Applicant applicant = Applicant.builder().applicantId(UUID.fromString(documentRequestDTO.getApplicant())).build();
        User uploadedByUser = User.builder().userID(UUID.fromString(documentRequestDTO.getUploadedBy())).build();
        User verifiedByUser = User.builder().userID(UUID.fromString(documentRequestDTO.getVerifiedBy())).build();
        //Handling of actual document upload
        if (!file.getContentType().equals("application/pdf")) {
            throw new DocumentFormatNotAllowedException("Only PDF format is allowed");
        }
        Document document = new Document();
        String filePath = fileStorageService.saveFile(file);
        document.setFileUrl(filePath);
        document.setLoanApplication(loanApplication);
        document.setApplicant(applicant);
        document.setDocumentStatus(DocumentStatus.valueOf(documentRequestDTO.getDocumentStatus()));
        document.setDocumentType(DocumentType.valueOf(documentRequestDTO.getDocumentType()));
        document.setUploadedBy(uploadedByUser);
        document.setVerifiedBy(verifiedByUser);
        document.setRemarks(documentRequestDTO.getRemarks());

        documentRepository.save(document);
        return DocumentResponseDTO.builder().DocumentId(document.getDocumentId()).loanApplication(document.getLoanApplication().getLoanID()).applicant(document.getApplicant().getApplicantId()).documentStatus(document.getDocumentStatus()).documentType(document.getDocumentType()).fileUrl(document.getFileUrl()).uploadedBy(document.getUploadedBy().getUserID()).verifiedBy(document.getVerifiedBy().getUserID()).remarks(document.getRemarks()).build();
    }

    @Override
    public DocumentResponseDTO getDocumentById(UUID documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));
        return DocumentResponseDTO.builder().DocumentId(document.getDocumentId()).loanApplication(document.getLoanApplication().getLoanID()).applicant(document.getApplicant().getApplicantId()).documentStatus(document.getDocumentStatus()).documentType(document.getDocumentType()).fileUrl(document.getFileUrl()).uploadedBy(document.getUploadedBy().getUserID()).verifiedBy(document.getVerifiedBy().getUserID()).remarks(document.getRemarks()).build();
    }

    @Override
    public List<DocumentResponseDTO> getAllDocumentsByLoanId(UUID loanId) {
        List<Document> documents = documentRepository.findAllByLoanApplication_LoanID(loanId);
        List<DocumentResponseDTO> documentResponseList = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            DocumentResponseDTO singleDocumentResponseDTO = DocumentResponseDTO.builder().DocumentId(documents.get(i).getDocumentId()).loanApplication(documents.get(i).getLoanApplication().getLoanID()).applicant(documents.get(i).getApplicant().getApplicantId()).documentStatus(documents.get(i).getDocumentStatus()).documentType(documents.get(i).getDocumentType()).fileUrl(documents.get(i).getFileUrl()).uploadedBy(documents.get(i).getUploadedBy().getUserID()).verifiedBy(documents.get(i).getVerifiedBy().getUserID()).remarks(documents.get(i).getRemarks()).build();

            documentResponseList.add(singleDocumentResponseDTO);
        }
        return documentResponseList;
    }

    @Override
    public List<DocumentResponseDTO> getAllDocumentsByApplicantId(UUID applicantId) {

        List<Document> documents = documentRepository.findAllByApplicant_applicantId(applicantId);
        List<DocumentResponseDTO> documentResponseList = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            DocumentResponseDTO singleDocumentResponseDTO = DocumentResponseDTO.builder().DocumentId(documents.get(i).getDocumentId()).loanApplication(documents.get(i).getLoanApplication().getLoanID()).applicant(documents.get(i).getApplicant().getApplicantId()).documentStatus(documents.get(i).getDocumentStatus()).documentType(documents.get(i).getDocumentType()).fileUrl(documents.get(i).getFileUrl()).uploadedBy(documents.get(i).getUploadedBy().getUserID()).verifiedBy(documents.get(i).getVerifiedBy().getUserID()).remarks(documents.get(i).getRemarks()).build();

            documentResponseList.add(singleDocumentResponseDTO);
        }
        return documentResponseList;
    }

    @Override
    public DocumentResponseDTO updateDocument(UUID documentId, DocumentRequestDTO documentRequestDTO) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));


        if (documentRequestDTO.getApplicant() != null) {
            Applicant applicant = Applicant.builder().applicantId(UUID.fromString(documentRequestDTO.getApplicant())).build();
            document.setApplicant(applicant);
        }

        if (documentRequestDTO.getDocumentType() != null) {
            document.setDocumentType(DocumentType.valueOf(documentRequestDTO.getDocumentType()));
        }

        if (documentRequestDTO.getFileUrl() != null) {
            document.setFileUrl(documentRequestDTO.getFileUrl());
        }

        if (documentRequestDTO.getRemarks() != null) {
            document.setRemarks(documentRequestDTO.getRemarks());
        }

        documentRepository.save(document);

        return DocumentResponseDTO.builder().DocumentId(document.getDocumentId()).loanApplication(document.getLoanApplication().getLoanID()).applicant(document.getApplicant().getApplicantId()).documentStatus(document.getDocumentStatus()).documentType(document.getDocumentType()).fileUrl(document.getFileUrl()).uploadedBy(document.getUploadedBy().getUserID()).verifiedBy(document.getVerifiedBy().getUserID()).remarks(document.getRemarks()).build();
    }

    @Override
    public DocumentResponseDTO updateDocumentFile(UUID documentId, MultipartFile file) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));

        if (!file.getContentType().equals("application/pdf")) {
            throw new DocumentFormatNotAllowedException("Only PDF format is allowed");
        }
        //deleting the previous one
        fileStorageService.deleteFile(document.getFileUrl());
        //uploading the new one
        String filePath = fileStorageService.saveFile(file);
        document.setFileUrl(filePath);
        documentRepository.save(document);
        return DocumentResponseDTO.builder().DocumentId(document.getDocumentId()).loanApplication(document.getLoanApplication().getLoanID()).applicant(document.getApplicant().getApplicantId()).documentStatus(document.getDocumentStatus()).documentType(document.getDocumentType()).fileUrl(document.getFileUrl()).uploadedBy(document.getUploadedBy().getUserID()).verifiedBy(document.getVerifiedBy().getUserID()).remarks(document.getRemarks()).build();
    }

    @Override
    public DocumentResponseDTO updateDocumentStatus(UUID documentId, DocumentStatus documentStatus, String Remarks) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));
        document.setDocumentStatus(documentStatus);
        document.setRemarks(Remarks);
        documentRepository.save(document);
        return DocumentResponseDTO.builder().DocumentId(document.getDocumentId()).loanApplication(document.getLoanApplication().getLoanID()).applicant(document.getApplicant().getApplicantId()).documentStatus(document.getDocumentStatus()).documentType(document.getDocumentType()).fileUrl(document.getFileUrl()).uploadedBy(document.getUploadedBy().getUserID()).verifiedBy(document.getVerifiedBy().getUserID()).remarks(document.getRemarks()).build();
    }

    @Override
    public void deleteAllDocumentsByLoanId(UUID loanId) {
        long count = documentRepository.deleteAllByLoanApplicationLoanID(loanId);

        if (count < 0) {
            throw new DocumentNotFoundException("No Documents Found for this loan application");
        }
    }

    @Override
    public void deleteDocumentsById(UUID documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));
        documentRepository.delete(document);
    }
}
