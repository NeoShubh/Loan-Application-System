package com.example.loanapplication.modules.documentmodule.service.impl;

import com.example.loanapplication.exception.document.DocumentNotFoundException;
import com.example.loanapplication.modules.documentmodule.dto.DocumentRequestDTO;
import com.example.loanapplication.modules.documentmodule.dto.DocumentResponseDTO;
import com.example.loanapplication.modules.documentmodule.entity.Document;
import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import com.example.loanapplication.modules.documentmodule.enums.DocumentType;
import com.example.loanapplication.modules.documentmodule.repository.DocumentRepository;
import com.example.loanapplication.modules.documentmodule.service.DocumentService;
import com.example.loanapplication.modules.loanapplicationmodule.entity.Applicant;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.usermodule.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentResponseDTO createDocument(DocumentRequestDTO documentRequestDTO) {

        LoanApplication loanApplication = LoanApplication.builder().loanID(UUID.fromString(documentRequestDTO.getLoanApplication())).build();
        Applicant applicant = Applicant.builder().applicantId(UUID.fromString(documentRequestDTO.getApplicant())).build();
        User uploadedByUser = User.builder().userID(UUID.fromString(documentRequestDTO.getUploadedBy())).build();
        User verifiedByUser = User.builder().userID(UUID.fromString(documentRequestDTO.getVerifiedBy())).build();
        Document document = new Document();
        document.setLoanApplication(loanApplication);
        document.setApplicant(applicant);
        document.setDocumentStatus(DocumentStatus.valueOf(documentRequestDTO.getDocumentStatus()));
        document.setDocumentType(DocumentType.valueOf(documentRequestDTO.getDocumentType()));
        document.setFileUrl(documentRequestDTO.getFileUrl());
        document.setUploadedBy(uploadedByUser);
        document.setVerifiedBy(verifiedByUser);
        document.setRemarks(documentRequestDTO.getRemarks());

        documentRepository.save(document);
        return DocumentResponseDTO.builder()
                .DocumentId(document.getDocumentId())
                .loanApplication(document.getLoanApplication().getLoanID())
                .applicant(document.getApplicant().getApplicantId())
                .documentStatus(document.getDocumentStatus())
                .documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy().getUserID())
                .verifiedBy(document.getVerifiedBy().getUserID())
                .remarks(document.getRemarks()).build();
    }

    @Override
    public DocumentResponseDTO getDocumentByID(String documentId) {
        Document document = documentRepository.findById(UUID.fromString(documentId)).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));
        return DocumentResponseDTO.builder()
                .DocumentId(document.getDocumentId())
                .loanApplication(document.getLoanApplication().getLoanID())
                .applicant(document.getApplicant().getApplicantId())
                .documentStatus(document.getDocumentStatus())
                .documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy().getUserID())
                .verifiedBy(document.getVerifiedBy().getUserID())
                .remarks(document.getRemarks()).build();
    }

    @Override
    public List<DocumentResponseDTO> getAllDocumentsByLoanId(String loanId) {
        List<Document> documents = documentRepository.findAllByLoanApplication_LoanID(UUID.fromString(loanId));
        List<DocumentResponseDTO> documentResponseList = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            DocumentResponseDTO singleDocumentResponseDTO = DocumentResponseDTO.builder()
                    .DocumentId(documents.get(i).getDocumentId())
                    .loanApplication(documents.get(i).getLoanApplication().getLoanID())
                    .applicant(documents.get(i).getApplicant().getApplicantId())
                    .documentStatus(documents.get(i).getDocumentStatus())
                    .documentType(documents.get(i).getDocumentType())
                    .fileUrl(documents.get(i).getFileUrl())
                    .uploadedBy(documents.get(i).getUploadedBy().getUserID())
                    .verifiedBy(documents.get(i).getVerifiedBy().getUserID())
                    .remarks(documents.get(i).getRemarks()).build();

            documentResponseList.add(singleDocumentResponseDTO);
        }
        return documentResponseList;
    }

    @Override
    public List<DocumentResponseDTO> getAllDocumentsByApplicantId(String applicantId) {

        List<Document> documents = documentRepository.findAllByApplicant_applicantId(UUID.fromString(applicantId));
        List<DocumentResponseDTO> documentResponseList = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            DocumentResponseDTO singleDocumentResponseDTO = DocumentResponseDTO.builder()
                    .DocumentId(documents.get(i).getDocumentId())
                    .loanApplication(documents.get(i).getLoanApplication().getLoanID())
                    .applicant(documents.get(i).getApplicant().getApplicantId())
                    .documentStatus(documents.get(i).getDocumentStatus())
                    .documentType(documents.get(i).getDocumentType())
                    .fileUrl(documents.get(i).getFileUrl())
                    .uploadedBy(documents.get(i).getUploadedBy().getUserID())
                    .verifiedBy(documents.get(i).getVerifiedBy().getUserID())
                    .remarks(documents.get(i).getRemarks()).build();

            documentResponseList.add(singleDocumentResponseDTO);
        }
        return documentResponseList;
    }

    @Override
    public DocumentResponseDTO updateDocument(String documentId, DocumentRequestDTO documentRequestDTO) {
        Document document = documentRepository.findById(UUID.fromString(documentId)).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));

        if (documentRequestDTO.getLoanApplication() != null) {
            LoanApplication loanApplication = LoanApplication.builder().loanID(UUID.fromString(documentRequestDTO.getLoanApplication())).build();
            document.setLoanApplication(loanApplication);
        }

        if (documentRequestDTO.getApplicant() != null) {
            Applicant applicant = Applicant.builder().applicantId(UUID.fromString(documentRequestDTO.getApplicant())).build();
            document.setApplicant(applicant);
        }

        if (documentRequestDTO.getDocumentStatus() != null) {
            document.setDocumentStatus(DocumentStatus.valueOf(documentRequestDTO.getDocumentStatus()));
        }

        if (documentRequestDTO.getDocumentType() != null) {
            document.setDocumentType(DocumentType.valueOf(documentRequestDTO.getDocumentType()));
        }

        if (documentRequestDTO.getFileUrl() != null) {
            document.setFileUrl(documentRequestDTO.getFileUrl());
        }

        if (documentRequestDTO.getUploadedBy() != null) {
            User user = User.builder().userID(UUID.fromString(documentRequestDTO.getUploadedBy())).build();
            document.setUploadedBy(user);
        }

        if (documentRequestDTO.getVerifiedBy() != null) {
            User user = User.builder().userID(UUID.fromString(documentRequestDTO.getVerifiedBy())).build();
            document.setVerifiedBy(user);
        }

        if (documentRequestDTO.getRemarks() != null) {
            document.setRemarks(documentRequestDTO.getRemarks());
        }

        documentRepository.save(document);

        return DocumentResponseDTO.builder()
                .DocumentId(document.getDocumentId())
                .loanApplication(document.getLoanApplication().getLoanID())
                .applicant(document.getApplicant().getApplicantId())
                .documentStatus(document.getDocumentStatus())
                .documentType(document.getDocumentType())
                .fileUrl(document.getFileUrl())
                .uploadedBy(document.getUploadedBy().getUserID())
                .verifiedBy(document.getVerifiedBy().getUserID())
                .remarks(document.getRemarks()).build();
    }

    @Override
    public void deleteAllDocumentByLoanId(String loanId) {
        long count = documentRepository.deleteAllByLoanApplicationLoanID(UUID.fromString(loanId));

        if(count < 0){
            throw  new DocumentNotFoundException("No Documents Found for this loan application");
        }
    }

    @Override
    public void deleteDocumentById(String documentId) {
        Document document = documentRepository.findById(UUID.fromString(documentId)).orElseThrow(() -> new DocumentNotFoundException("Document is not available"));
        documentRepository.delete(document);
    }
}
