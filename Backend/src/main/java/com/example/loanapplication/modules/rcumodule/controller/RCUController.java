package com.example.loanapplication.modules.rcumodule.controller;


import com.example.loanapplication.modules.rcumodule.dto.standardDTOs.RCUCaseResponseDTO;
import com.example.loanapplication.modules.rcumodule.service.RCUService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RCUController {

    private final RCUService rcuService;

    public RCUController(RCUService rcuService) {
        this.rcuService = rcuService;
    }

    @PostMapping("/loans/{loanId}/rcu")
    ResponseEntity<RCUCaseResponseDTO> CreateRCUCase(@PathVariable String loanId){
       RCUCaseResponseDTO rcuCaseResponseDTO = rcuService.CreateRCUCase(UUID.fromString(loanId));
        return ResponseEntity.status(HttpStatus.CREATED).body(rcuCaseResponseDTO);
    }

    @DeleteMapping("/rcu/{rcuCaseId}")
    ResponseEntity<String> DeleteRCUCasebyId(@PathVariable String rcuCaseId){
       rcuService.DeleteRCUCaseById(UUID.fromString(rcuCaseId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted Successfully");
    }
}