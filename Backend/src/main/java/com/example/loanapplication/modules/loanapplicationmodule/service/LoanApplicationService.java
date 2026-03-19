package com.example.loanapplication.modules.loanapplicationmodule.service;

import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationResponseDTO;

import java.util.List;
//import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;

public interface LoanApplicationService {

    //Loan Application methods
   LoanApplicationResponseDTO createLoanApplication(LoanApplicationRequestDTO loanApplicationRequestDTO);
   List<LoanApplicationResponseDTO> getAllLoanApplicationByUserID(String userId);
   LoanApplicationResponseDTO getLoanApplicationById(String loanId);
   LoanApplicationResponseDTO updateLoanApplication(LoanApplicationRequestDTO loanApplicationRequestDTO);
   void deleteLoanApplication(String loanId);
   //Loan Application History methods
    LoanStageHistoryResponseDTO createLoanStageHistory(LoanStageHistoryRequestDTO loanStageHistoryRequestDTO);
    List<LoanStageHistoryResponseDTO> getAllLoanStageHistoryByLoanId(String LoanId);
    LoanStageHistoryResponseDTO getLoanStageHistoryById(String loanStageHistoryId);
    void deleteLoanStageHistoryById(String loanStageHistoryId);
    void deleteAllLoanStageHistoryByLoanId(String LoanId);
    LoanStageHistoryResponseDTO updateLoanStageHistory(String loanStageHistoryId);

}
