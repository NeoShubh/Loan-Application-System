package com.example.loanapplication.modules.loanapplicationmodule.service.impl;

import com.example.loanapplication.exception.user.LoanApplicationNotFoundException;
import com.example.loanapplication.exception.user.UserNotFoundException;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.loanapplicationmodule.enums.CreditStatus;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanType;
import com.example.loanapplication.modules.loanapplicationmodule.enums.RCUStatus;
import com.example.loanapplication.modules.loanapplicationmodule.repository.LoanApplicationRepository;
import com.example.loanapplication.modules.loanapplicationmodule.repository.LoanStageHistoryRepository;
import com.example.loanapplication.modules.loanapplicationmodule.service.LoanApplicationService;
import com.example.loanapplication.modules.usermodule.entity.User;
import com.example.loanapplication.modules.usermodule.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanStageHistoryRepository loanStageHistoryRepository;
    private final UserService userService;


    public LoanApplicationServiceImpl(LoanApplicationRepository loanApplicationRepository, LoanStageHistoryRepository loanStageHistoryRepository, UserService userService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanStageHistoryRepository = loanStageHistoryRepository;
        this.userService = userService;
    }

    @Override
    public LoanApplicationResponseDTO createLoanApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        User user = User.builder().userID(UUID.fromString(loanApplicationRequestDTO.getCreatedBy())).build();

        LoanApplication loanApplication = LoanApplication.builder()
                .loanType(LoanType.valueOf(loanApplicationRequestDTO.getLoanType()))
                .loanStage(LoanStage.valueOf(loanApplicationRequestDTO.getLoanStage()))
                .rcuStatus(RCUStatus.valueOf(loanApplicationRequestDTO.getRcuStatus()))
                .creditStatus(CreditStatus.valueOf(loanApplicationRequestDTO.getCreditStatus()))
                .createdBy(user).build();

        // Loan Stage history code will be due here

        loanApplicationRepository.save(loanApplication);

        return LoanApplicationResponseDTO.builder()
                .loanID(loanApplication.getLoanID())
                .loanType(loanApplication.getLoanType())
                .loanStage(loanApplication.getLoanStage())
                .rcuStatus(loanApplication.getRcuStatus())
                .creditStatus(loanApplication.getCreditStatus())
                .createdAt(loanApplication.getCreatedAt())
                .createdBy(loanApplication.getCreatedBy().getUserID())
                .updatedAt(loanApplication.getUpdatedAt()).build();
    }

    @Override
    public List<LoanApplicationResponseDTO> getAllLoanApplicationByUserID(String userId) {
        List<LoanApplicationResponseDTO> responseList = new ArrayList<>();

        if (userService.isUserAvailable(UUID.fromString(userId))) {
            List<LoanApplication> loansList = loanApplicationRepository.findByCreatedByUserID(UUID.fromString(userId));
            for (int i = 0; i < loansList.size(); i++) {
                LoanApplicationResponseDTO singleLoanResponseDTO = LoanApplicationResponseDTO.builder()
                        .loanID(loansList.get(i).getLoanID())
                        .loanType(loansList.get(i).getLoanType())
                        .loanStage(loansList.get(i).getLoanStage())
                        .rcuStatus(loansList.get(i).getRcuStatus())
                        .creditStatus(loansList.get(i).getCreditStatus())
                        .createdBy(loansList.get(i).getCreatedBy().getUserID())
                        .createdAt(loansList.get(i).getCreatedAt())
                        .updatedAt(loansList.get(i).getUpdatedAt()).build();
                responseList.add(singleLoanResponseDTO);
            }
        } else {
            throw new UserNotFoundException("User doesn't exist");
        }

        return responseList;
    }

    @Override
    public LoanApplicationResponseDTO getLoanApplicationById(String loanId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(UUID.fromString(loanId)).orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application Not found"));
        return LoanApplicationResponseDTO.builder()
                .loanID(loanApplication.getLoanID())
                .loanType(loanApplication.getLoanType())
                .loanStage(loanApplication.getLoanStage())
                .rcuStatus(loanApplication.getRcuStatus())
                .creditStatus(loanApplication.getCreditStatus())
                .createdBy(loanApplication.getCreatedBy().getUserID())
                .createdAt(loanApplication.getCreatedAt())
                .updatedAt(loanApplication.getUpdatedAt()).build();
    }

    @Override
    public LoanApplicationResponseDTO updateLoanApplication(String loanID,LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LoanApplication loanApplication = loanApplicationRepository.findById(UUID.fromString(loanID)).orElseThrow(()-> new LoanApplicationNotFoundException("Loan Application Not Found"));

        if(loanApplicationRequestDTO.getLoanType()!=null){
            loanApplication.setLoanType(LoanType.valueOf(loanApplicationRequestDTO.getLoanType()));
        }

        if(loanApplicationRequestDTO.getLoanStage() != null){
            // Loan Stage history code will be due here As the stage changes new history should be created by this change
            loanApplication.setLoanStage(LoanStage.valueOf(loanApplicationRequestDTO.getLoanStage()));
        }

        if(loanApplicationRequestDTO.getRcuStatus() != null){
            loanApplication.setRcuStatus(RCUStatus.valueOf(loanApplicationRequestDTO.getRcuStatus()));
        }

        if(loanApplicationRequestDTO.getCreditStatus() != null){
            loanApplication.setCreditStatus(CreditStatus.valueOf(loanApplicationRequestDTO.getCreditStatus()));
        }

        if(loanApplicationRequestDTO.getCreatedBy() != null){
            User user = User.builder().userID(UUID.fromString(loanApplicationRequestDTO.getCreatedBy())).build();
            loanApplication.setCreatedBy(user);
        }

        loanApplicationRepository.save(loanApplication);

        return LoanApplicationResponseDTO.builder()
                .loanID(loanApplication.getLoanID())
                .loanType(loanApplication.getLoanType())
                .loanStage(loanApplication.getLoanStage())
                .rcuStatus(loanApplication.getRcuStatus())
                .creditStatus(loanApplication.getCreditStatus())
                .createdBy(loanApplication.getCreatedBy().getUserID())
                .createdAt(loanApplication.getCreatedAt())
                .updatedAt(loanApplication.getUpdatedAt())
                .build();
    }

    @Override
    public void deleteLoanApplication(String loanId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(UUID.fromString(loanId)).orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application Not found"));
        //while deleting the loan application its history, documents and applicant should be deleted
        loanApplicationRepository.deleteById(loanApplication.getLoanID());
    }

    @Override
    public LoanStageHistoryResponseDTO createLoanStageHistory(LoanStageHistoryRequestDTO loanStageHistoryRequestDTO) {
        return null;
    }

    @Override
    public List<LoanStageHistoryResponseDTO> getAllLoanStageHistoryByLoanId(String LoanId) {
        return List.of();
    }

    @Override
    public LoanStageHistoryResponseDTO getLoanStageHistoryById(String loanStageHistoryId) {
        return null;
    }

    @Override
    public void deleteLoanStageHistoryById(String loanStageHistoryId) {

    }

    @Override
    public void deleteAllLoanStageHistoryByLoanId(String LoanId) {

    }

    @Override
    public LoanStageHistoryResponseDTO updateLoanStageHistory(String loanStageHistoryId) {
        return null;
    }
}
