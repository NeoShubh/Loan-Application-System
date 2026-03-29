package com.example.loanapplication.modules.rcumodule.dto.standardDTOs;

import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.rcumodule.enums.RCUStatus;
import com.example.loanapplication.modules.usermodule.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RCUCaseResponseDTO {

    private UUID rcuCaseId;
    private UUID loan;
    private RCUStatus rcuStatus;
    private UUID assignedTo;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
}
