package com.example.loanapplication.modules.rcumodule.service;

import com.example.loanapplication.modules.rcumodule.dto.standardDTOs.RCUCaseResponseDTO;

import java.util.UUID;

public interface RCUService {

    RCUCaseResponseDTO CreateRCUCase(UUID loanId);

}
