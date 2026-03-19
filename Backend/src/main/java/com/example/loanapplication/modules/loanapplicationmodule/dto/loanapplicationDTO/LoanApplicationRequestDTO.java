package com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO;

import com.example.loanapplication.modules.loanapplicationmodule.enums.CreditStatus;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanType;
import com.example.loanapplication.modules.loanapplicationmodule.enums.RCUStatus;
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
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequestDTO {

    @NotNull(message = "Loan type can not be null")
    private String loanType;

    @NotNull(message = "Loan stage can not be null")
    private String loanStage;

    @NotNull(message = "RCU status can not be null")
    private String rcuStatus;

    @NotNull(message = "credit status can not be null")
    private String creditStatus;

    @NotNull(message = "creator can not be null")
    private String createdBy;
}
