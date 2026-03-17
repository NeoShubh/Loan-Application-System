package com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO;

import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.modules.usermodule.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanStageHistoryRequestDTO {
    @NotNull(message = "loan application ID can not be blank")
    private String loanApplicationId;
    @NotNull(message = "old Stage can not be null")
    private LoanStage oldStage;
    @NotNull(message = "current Stage can not be null")
    private LoanStage currentStage;
    @NotNull(message = "changed by field can not be null")
    private String changedBy;

}
