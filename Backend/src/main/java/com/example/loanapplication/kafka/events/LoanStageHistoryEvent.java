package com.example.loanapplication.kafka.events;

import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanStageHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoanStageHistoryEvent {
    private String loanID;
    private String userID;
    private LoanStageHistoryRequestDTO loanStageHistoryRequestDTO;
}
