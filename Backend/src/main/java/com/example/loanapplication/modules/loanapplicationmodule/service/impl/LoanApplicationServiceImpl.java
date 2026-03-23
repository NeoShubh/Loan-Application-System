package com.example.loanapplication.modules.loanapplicationmodule.service.impl;

import com.example.loanapplication.exception.loanapplication.LoanApplicationNotFoundException;
import com.example.loanapplication.exception.loanapplication.LoanStageHistoryNotFoundException;
import com.example.loanapplication.exception.user.UserNotFoundException;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanStageHistory;
import com.example.loanapplication.modules.loanapplicationmodule.enums.CreditStatus;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanType;
import com.example.loanapplication.modules.loanapplicationmodule.enums.RCUStatus;
import com.example.loanapplication.modules.loanapplicationmodule.repository.LoanApplicationRepository;
import com.example.loanapplication.modules.loanapplicationmodule.repository.LoanStageHistoryRepository;
import com.example.loanapplication.modules.loanapplicationmodule.service.ApplicantService;
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
    private final ApplicantService applicantService;

    public LoanApplicationServiceImpl(
            LoanApplicationRepository loanApplicationRepository,
            LoanStageHistoryRepository loanStageHistoryRepository,
            UserService userService,
            ApplicantService applicantService
    ) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanStageHistoryRepository = loanStageHistoryRepository;
        this.userService = userService;
        this.applicantService = applicantService;
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

        loanApplicationRepository.save(loanApplication);

        // Loan Stage History Creation
        LoanStageHistoryRequestDTO loanStageHistoryRequestDTO = LoanStageHistoryRequestDTO.builder()
                .loanApplicationId(String.valueOf(loanApplication.getLoanID()))
                .changedBy(String.valueOf(user.getUserID()))
                .oldStage(LoanStage.NOT_INITIATED)
                .currentStage(loanApplication.getLoanStage()).build();

        LoanStageHistoryResponseDTO loanStageHistoryResponseDTO =
                createLoanStageHistory(String.valueOf(loanApplication.getLoanID()), String.valueOf(user.getUserID()), loanStageHistoryRequestDTO);


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
    public LoanApplicationResponseDTO updateLoanApplication(String loanID, LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LoanApplication loanApplication = loanApplicationRepository.findById(UUID.fromString(loanID)).orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application Not Found"));
        User user = User.builder().userID(UUID.fromString(loanApplicationRequestDTO.getCreatedBy())).build();
        if (loanApplicationRequestDTO.getLoanType() != null) {
            loanApplication.setLoanType(LoanType.valueOf(loanApplicationRequestDTO.getLoanType()));
        }

        if (loanApplicationRequestDTO.getLoanStage() != null) {
            loanApplication.setLoanStage(LoanStage.valueOf(loanApplicationRequestDTO.getLoanStage()));
            //Loan Stage history creation
            LoanStageHistoryRequestDTO loanStageHistoryRequestDTO = LoanStageHistoryRequestDTO.builder()
                    .loanApplicationId(String.valueOf(loanApplication.getLoanID()))
                    .changedBy(String.valueOf(user.getUserID()))
                    .oldStage(loanApplication.getLoanStage())
                    .currentStage(LoanStage.valueOf(loanApplicationRequestDTO.getLoanStage())).build();

            LoanStageHistoryResponseDTO loanStageHistoryResponseDTO =
                    createLoanStageHistory(String.valueOf(loanApplication.getLoanID()), String.valueOf(user.getUserID()), loanStageHistoryRequestDTO);

        }

        if (loanApplicationRequestDTO.getRcuStatus() != null) {
            loanApplication.setRcuStatus(RCUStatus.valueOf(loanApplicationRequestDTO.getRcuStatus()));
        }

        if (loanApplicationRequestDTO.getCreditStatus() != null) {
            loanApplication.setCreditStatus(CreditStatus.valueOf(loanApplicationRequestDTO.getCreditStatus()));
        }

        if (loanApplicationRequestDTO.getCreatedBy() != null) {
//            User user = User.builder().userID(UUID.fromString(loanApplicationRequestDTO.getCreatedBy())).build();
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
        deleteAllLoanStageHistoryByLoanId(loanId);
        applicantService.deleteAllApplicantByLoanId(String.valueOf(loanApplication.getLoanID()));
        loanApplicationRepository.deleteById(loanApplication.getLoanID());

    }

    @Override
    public boolean isLoanApplicationExists(String loanID) {
        return loanApplicationRepository.existsByLoanID(UUID.fromString(loanID));
    }

    //LOAN STAGE HISTORY
    @Override
    public LoanStageHistoryResponseDTO createLoanStageHistory(String loanApplicationID, String userID, LoanStageHistoryRequestDTO loanStageHistoryRequestDTO) {

        LoanStageHistory loanStageHistory = new LoanStageHistory();

        if (isLoanApplicationExists(loanApplicationID)) {

            LoanApplication loanApplication = LoanApplication.builder().loanID(UUID.fromString(loanApplicationID)).build();
            User user = User.builder().userID(UUID.fromString(userID)).build();

            loanStageHistory.setLoanApplication(loanApplication);
            loanStageHistory.setChangedBy(user);
            loanStageHistory.setOldStage(loanStageHistoryRequestDTO.getOldStage());
            loanStageHistory.setCurrentStage(loanStageHistoryRequestDTO.getCurrentStage());

            loanStageHistoryRepository.save(loanStageHistory);

        } else {
            throw new LoanApplicationNotFoundException("Loan Application is not found.");
        }

        return LoanStageHistoryResponseDTO.builder()
                .loanStageHistoryId(loanStageHistory.getLoanStageHistoryId())
                .loanApplicationId(loanStageHistory.getLoanApplication().getLoanID())
                .oldStage(loanStageHistory.getOldStage())
                .currentStage(loanStageHistory.getCurrentStage())
                .changedAt(loanStageHistory.getChangedAt())
                .changedBy(loanStageHistory.getChangedBy().getUserID()).build();

    }

    @Override
    public List<LoanStageHistoryResponseDTO> getAllLoanStageHistoryByLoanId(String loanId) {
        List<LoanStageHistory> loanStageHistories = loanStageHistoryRepository.findByLoanApplicationLoanID(UUID.fromString(loanId));
        List<LoanStageHistoryResponseDTO> loanStageHistorylist = new ArrayList<>();

        for (int i = 0; i < loanStageHistories.size(); i++) {
            LoanStageHistoryResponseDTO singleLoanStageHistoryResponseDTO = LoanStageHistoryResponseDTO.builder()
                    .loanStageHistoryId(loanStageHistories.get(i).getLoanStageHistoryId())
                    .loanApplicationId(loanStageHistories.get(i).getLoanApplication().getLoanID())
                    .oldStage(loanStageHistories.get(i).getOldStage())
                    .currentStage(loanStageHistories.get(i).getCurrentStage())
                    .changedAt(loanStageHistories.get(i).getChangedAt())
                    .changedBy(loanStageHistories.get(i).getChangedBy().getUserID()).build();
            loanStageHistorylist.add(singleLoanStageHistoryResponseDTO);
        }

        return loanStageHistorylist;
    }

    @Override
    public LoanStageHistoryResponseDTO getLoanStageHistoryById(String loanStageHistoryId) {

        LoanStageHistory loanstageHistory = loanStageHistoryRepository.findById(UUID.fromString(loanStageHistoryId)).orElseThrow(() -> new LoanStageHistoryNotFoundException("Loan Stage History Not Found."));

        return LoanStageHistoryResponseDTO.builder()
                .loanStageHistoryId(loanstageHistory.getLoanStageHistoryId())
                .loanApplicationId(loanstageHistory.getLoanApplication().getLoanID())
                .oldStage(loanstageHistory.getOldStage())
                .currentStage(loanstageHistory.getCurrentStage())
                .changedAt(loanstageHistory.getChangedAt())
                .changedBy(loanstageHistory.getChangedBy().getUserID()).build();
    }

    @Override
    public void deleteLoanStageHistoryById(String loanStageHistoryId) {
        LoanStageHistory loanstageHistory = loanStageHistoryRepository.findById(UUID.fromString(loanStageHistoryId)).orElseThrow(() -> new LoanStageHistoryNotFoundException("Loan Stage History Not Found."));
        loanStageHistoryRepository.delete(loanstageHistory);
    }

    @Override
    public void deleteAllLoanStageHistoryByLoanId(String LoanId) {
        long count = loanStageHistoryRepository.deleteAllByLoanApplicationLoanID(UUID.fromString(LoanId));

        if (count == 0) {
            throw new LoanStageHistoryNotFoundException("No history found to delete");
        }
    }

    @Override
    public LoanStageHistoryResponseDTO updateLoanStageHistory(String loanStageHistoryId, LoanStageHistoryRequestDTO loanStageHistoryRequestDTO) {

        //check loan stage history exists or not
        LoanStageHistory loanstageHistory = loanStageHistoryRepository.findById(UUID.fromString(loanStageHistoryId)).orElseThrow(() -> new LoanStageHistoryNotFoundException("Loan Stage History Not Found."));

        // checking loan application exists or not
        if (isLoanApplicationExists(loanStageHistoryRequestDTO.getLoanApplicationId())) {

            //setting the new loan application ID
            if (loanStageHistoryRequestDTO.getLoanApplicationId() != null) {
                LoanApplication loanApplication = LoanApplication.builder().loanID(UUID.fromString(loanStageHistoryRequestDTO.getLoanApplicationId())).build();
                loanstageHistory.setLoanApplication(loanApplication);
            }

            //setting the new current stage
            if (loanStageHistoryRequestDTO.getCurrentStage() != null) {
                loanstageHistory.setCurrentStage(loanStageHistoryRequestDTO.getCurrentStage());
            }

            //setting the old current stage
            if (loanStageHistoryRequestDTO.getOldStage() != null) {
                loanstageHistory.setOldStage(loanStageHistoryRequestDTO.getOldStage());
            }

            //setting the new user or changed by field
            if (loanStageHistoryRequestDTO.getChangedBy() != null) {
                User user = User.builder().userID(UUID.fromString(loanStageHistoryRequestDTO.getChangedBy())).build();
                loanstageHistory.setChangedBy(user);
            }

        }
        //saving the changes
        loanStageHistoryRepository.save(loanstageHistory);
        return LoanStageHistoryResponseDTO.builder()
                .loanStageHistoryId(loanstageHistory.getLoanStageHistoryId())
                .loanApplicationId(loanstageHistory.getLoanApplication().getLoanID())
                .oldStage(loanstageHistory.getOldStage())
                .currentStage(loanstageHistory.getCurrentStage())
                .changedAt(loanstageHistory.getChangedAt())
                .changedBy(loanstageHistory.getChangedBy().getUserID()).build();
    }
}
