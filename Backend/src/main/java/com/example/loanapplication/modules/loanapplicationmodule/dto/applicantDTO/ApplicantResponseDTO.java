package com.example.loanapplication.modules.loanapplicationmodule.dto.applicantDTO;

import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.loanapplicationmodule.enums.ApplicantType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantResponseDTO {
    private UUID applicantId;
    private UUID loanApplication;
    private String name;
    private String panNumber;
    private String address;
    private ApplicantType applicantType;
    private LocalDateTime createdAt;
}
