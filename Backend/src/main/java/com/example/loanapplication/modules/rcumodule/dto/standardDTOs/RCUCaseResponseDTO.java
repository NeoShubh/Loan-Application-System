package com.example.loanapplication.modules.rcumodule.dto.standardDTOs;

import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.rcumodule.enums.RCUStatus;
import com.example.loanapplication.modules.usermodule.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class RCUCaseResponseDTO {

    private UUID rcuCaseId;
    private UUID loan;
    private RCUStatus rcuStatus;
    private UUID assignedTo;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
}
