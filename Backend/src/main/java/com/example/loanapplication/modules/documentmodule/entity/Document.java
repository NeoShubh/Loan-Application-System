package com.example.loanapplication.modules.documentmodule.entity;

import com.example.loanapplication.exception.applicant.ApplicantNotFoundException;
import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import com.example.loanapplication.modules.documentmodule.enums.DocumentType;
import com.example.loanapplication.modules.loanapplicationmodule.entity.Applicant;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.usermodule.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID documentId;

    @ManyToOne
    private LoanApplication loanApplication;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Applicant applicant;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus = DocumentStatus.UPLOADED;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String fileUrl;

    @ManyToOne
    private User uploadedBy;

    private LocalDateTime uploadedAt;

    @ManyToOne
    private User verifiedBy;

    private LocalDateTime verifiedAt;

    private String remarks;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.uploadedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}