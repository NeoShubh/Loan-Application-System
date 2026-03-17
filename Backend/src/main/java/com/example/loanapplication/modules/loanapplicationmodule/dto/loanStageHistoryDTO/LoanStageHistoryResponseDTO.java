package com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO;

import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.modules.usermodule.entity.User;
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
public class LoanStageHistoryResponseDTO {

    private UUID loanStageHistoryId;

    private UUID loanApplicationId;

    private LoanStage oldStage;

    private LoanStage currentStage;

    private UUID changedBy;

    private LocalDateTime changedAt;
}
