package com.example.loanapplication.modules.rcumodule.dto.standardDTOs;

import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.rcumodule.enums.RCUStatus;
import com.example.loanapplication.modules.usermodule.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RCUCaseRequestDTO {
    @NotNull(message = "RCU Case ID can not be null")
    private String rcuCaseId;
    @NotNull(message = "loanID can not be null")
    private String loan;
    @NotNull(message = "RCU status can not be null")
    private RCUStatus rcuStatus;
    @NotNull(message = "Assigned User can not be null")
    private String  assignedTo;

}
