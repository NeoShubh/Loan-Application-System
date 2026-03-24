package com.example.loanapplication.modules.loanapplicationmodule.controller;

import com.example.loanapplication.modules.loanapplicationmodule.dto.applicantDTO.ApplicantRequestDTO;
import com.example.loanapplication.modules.loanapplicationmodule.dto.applicantDTO.ApplicantResponseDTO;
import com.example.loanapplication.modules.loanapplicationmodule.service.ApplicantService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping("/applicants")
    ResponseEntity<ApplicantResponseDTO> createApplicant( @Valid @RequestBody ApplicantRequestDTO applicantRequestDTO){
        ApplicantResponseDTO applicantResponseDTO = applicantService.createApplicant(applicantRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicantResponseDTO);
    }

    @PutMapping("/applicants/{ApplicantId}")
    ResponseEntity<ApplicantResponseDTO> updateApplicant(@PathVariable  String ApplicantId,@RequestBody ApplicantRequestDTO applicantRequestDTO){
    ApplicantResponseDTO applicantResponseDTO = applicantService.updateApplicant(ApplicantId,applicantRequestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(applicantResponseDTO);
    }

    @DeleteMapping("/applicants/{ApplicantId}")
    ResponseEntity<String> deleteApplicantById(@PathVariable String ApplicantId){
        applicantService.deleteApplicantById(ApplicantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("applicant deleted");
    }

    @DeleteMapping("/{LoanId}/applicants")
    ResponseEntity<String> deleteAllApplicantByLoanId(@PathVariable String LoanId){
        applicantService.deleteAllApplicantByLoanId(LoanId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("applicants deleted successfully");
    }

    @GetMapping("/applicants/{ApplicantId}")
    ResponseEntity<ApplicantResponseDTO> getApplicantById(@PathVariable  String ApplicantId){
       ApplicantResponseDTO applicantResponseDTO =  applicantService.getApplicantById(ApplicantId);
        return ResponseEntity.status(HttpStatus.OK).body(applicantResponseDTO);
    }

    @GetMapping("/{loanId}/applicants/primary")
    ResponseEntity<ApplicantResponseDTO>getPrimaryApplicant(@PathVariable String loanId){
        ApplicantResponseDTO applicantResponseDTO = applicantService.getPrimaryApplicant(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(applicantResponseDTO);
    }

    @GetMapping("/{loanId}/applicants/secondary")
    ResponseEntity<List<ApplicantResponseDTO>>getAllSecondaryApplicant(@PathVariable String loanId){
        List <ApplicantResponseDTO> responseDTOList = applicantService.getAllSecondaryApplicant(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }

    @GetMapping("/{loanId}/applicants")
    ResponseEntity<List<ApplicantResponseDTO>>getAllApplicant(@PathVariable String loanId){
        List <ApplicantResponseDTO> responseDTOList = applicantService.getAllApplicant(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }
}
