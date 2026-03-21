package com.example.loanapplication.modules.loanapplicationmodule.service.impl;

import com.example.loanapplication.exception.applicant.ApplicantNotFoundException;
import com.example.loanapplication.exception.loanapplication.LoanApplicationNotFoundException;
import com.example.loanapplication.modules.loanapplicationmodule.dto.applicantDTO.ApplicantRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.applicantDTO.ApplicantResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.entity.Applicant;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.loanapplicationmodule.enums.ApplicantType;
import com.example.loanapplication.modules.loanapplicationmodule.repository.ApplicantRepository;
import com.example.loanapplication.modules.loanapplicationmodule.service.ApplicantService;
import com.example.loanapplication.modules.loanapplicationmodule.service.LoanApplicationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final LoanApplicationService loanApplicationService;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, LoanApplicationService loanApplicationService) {
        this.applicantRepository = applicantRepository;
        this.loanApplicationService = loanApplicationService;
    }

    @Override
    public ApplicantResponseDTO createApplicant(ApplicantRequestDTO applicantRequestDTO) {
        Applicant applicant = new Applicant();
        if (loanApplicationService.isLoanApplicationExists(applicantRequestDTO.getLoanApplication())) {
            applicant.setLoanApplication(LoanApplication.builder().loanID(UUID.fromString(applicantRequestDTO.getLoanApplication())).build());
            applicant.setName(applicantRequestDTO.getName());
            applicant.setPanNumber(applicantRequestDTO.getPanNumber());
            applicant.setAddress(applicantRequestDTO.getAddress());
            applicant.setApplicantType(applicantRequestDTO.getApplicantType());
        } else {
            throw new LoanApplicationNotFoundException("Loan Application not found");
        }
        applicantRepository.save(applicant);
        return ApplicantResponseDTO.builder()
                .applicantId(applicant.getApplicantId())
                .loanApplication(applicant.getLoanApplication().getLoanID())
                .name(applicant.getName())
                .panNumber(applicant.getPanNumber())
                .address(applicant.getAddress())
                .applicantType(applicant.getApplicantType())
                .createdAt(applicant.getCreatedAt())
                .build();
    }

    @Override
    public ApplicantResponseDTO updateApplicant(String ApplicantId, ApplicantRequestDTO applicantRequestDTO) {
        Applicant applicant = applicantRepository.findById(UUID.fromString(ApplicantId)).orElseThrow(() -> new ApplicantNotFoundException("Applicant Not Found"));

        if (loanApplicationService.isLoanApplicationExists(applicantRequestDTO.getLoanApplication())) {

            if(applicantRequestDTO.getLoanApplication() != null){
                applicant.setLoanApplication(LoanApplication.builder().loanID(UUID.fromString(applicantRequestDTO.getLoanApplication())).build());
            }

            if(applicantRequestDTO.getApplicantType() != null){
                applicant.setApplicantType(applicantRequestDTO.getApplicantType());
            }

            if(applicantRequestDTO.getName() != null){
                applicant.setName(applicantRequestDTO.getName());
            }

            if(applicantRequestDTO.getPanNumber() != null){
                applicant.setPanNumber(applicantRequestDTO.getPanNumber());
            }

            if(applicantRequestDTO.getAddress() != null){
                applicant.setAddress(applicantRequestDTO.getAddress());
            }
            applicantRepository.save(applicant);

        } else {
            throw new LoanApplicationNotFoundException("Loan Application not found");
        }
        return ApplicantResponseDTO.builder()
                .applicantId(applicant.getApplicantId())
                .loanApplication(applicant.getApplicantId())
                .applicantType(applicant.getApplicantType())
                .name(applicant.getName())
                .address(applicant.getAddress())
                .panNumber(applicant.getPanNumber())
                .createdAt(applicant.getCreatedAt()).build();
    }

    @Override
    public void deleteApplicantById(String ApplicantId) {
        Applicant applicant = applicantRepository.findById(UUID.fromString(ApplicantId)).orElseThrow(() -> new ApplicantNotFoundException("Applicant Not Found"));
        applicantRepository.deleteById(applicant.getApplicantId());
    }

    @Override
    public void deleteAllApplicantByLoanId(String loanId) {
        long count = applicantRepository.deleteAllByLoanApplicationLoanID(UUID.fromString(loanId));
        if(count < 0){
            throw new ApplicantNotFoundException("No Applicants found for this loan application");
        }
    }

    @Override
    public ApplicantResponseDTO getApplicantById(String ApplicantId) {
        Applicant applicant = applicantRepository.findById(UUID.fromString(ApplicantId)).orElseThrow(() -> new ApplicantNotFoundException("Applicant Not Found"));

        return ApplicantResponseDTO.builder()
                .applicantId(applicant.getApplicantId())
                .loanApplication(applicant.getLoanApplication().getLoanID())
                .name(applicant.getName())
                .panNumber(applicant.getPanNumber())
                .address(applicant.getAddress())
                .applicantType(applicant.getApplicantType())
                .createdAt(applicant.getCreatedAt()).build();
    }

    @Override
    public ApplicantResponseDTO getPrimaryApplicant(String loanId) {


        Applicant applicant = applicantRepository.findByLoanApplication_LoanIDAndApplicantType(UUID.fromString(loanId), ApplicantType.PRIMARY);
        if(applicant==null){
         throw new ApplicantNotFoundException("Primary Applicant Not Found.");
        }

        return ApplicantResponseDTO.builder()
                .applicantId(applicant.getApplicantId())
                .loanApplication(applicant.getLoanApplication().getLoanID())
                .name(applicant.getName())
                .panNumber(applicant.getPanNumber())
                .address(applicant.getAddress())
                .applicantType(applicant.getApplicantType())
                .createdAt(applicant.getCreatedAt()).build();
    }

    @Override
    public List<ApplicantResponseDTO> getAllSecondaryApplicant(String loanId) {

        List <Applicant> applicants = applicantRepository.findAllByLoanApplication_LoanIDAndApplicantType(UUID.fromString(loanId),ApplicantType.SECONDARY);
        List <ApplicantResponseDTO> responseApplicants = new ArrayList<>();
        for (int i = 0; i < applicants.size(); i++) {
            ApplicantResponseDTO applicantResponseDTO = ApplicantResponseDTO.builder()
                    .applicantId(applicants.get(i).getApplicantId())
                    .loanApplication(applicants.get(i).getLoanApplication().getLoanID())
                    .name(applicants.get(i).getName())
                    .panNumber(applicants.get(i).getPanNumber())
                    .address(applicants.get(i).getAddress())
                    .applicantType(applicants.get(i).getApplicantType())
                    .createdAt(applicants.get(i).getCreatedAt()).build();
            responseApplicants.add(applicantResponseDTO);
        }
        return responseApplicants;
    }

    @Override
    public List<ApplicantResponseDTO> getAllApplicant(String loanId) {
        List<Applicant> applicants = applicantRepository.findByLoanApplicationLoanID(UUID.fromString(loanId));
        List <ApplicantResponseDTO> responseApplicants = new ArrayList<>();
        for (int i = 0; i < applicants.size(); i++) {
           ApplicantResponseDTO applicantResponseDTO = ApplicantResponseDTO.builder()
                   .applicantId(applicants.get(i).getApplicantId())
                   .loanApplication(applicants.get(i).getLoanApplication().getLoanID())
                   .name(applicants.get(i).getName())
                   .panNumber(applicants.get(i).getPanNumber())
                   .address(applicants.get(i).getAddress())
                   .applicantType(applicants.get(i).getApplicantType())
                   .createdAt(applicants.get(i).getCreatedAt()).build();
           responseApplicants.add(applicantResponseDTO);
        }

        return responseApplicants;
    }
}
