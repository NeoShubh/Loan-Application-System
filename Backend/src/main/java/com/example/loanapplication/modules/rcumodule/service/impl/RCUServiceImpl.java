package com.example.loanapplication.modules.rcumodule.service.impl;

import com.example.loanapplication.modules.rcumodule.dto.standardDTOs.RCUCaseResponseDTO;
import com.example.loanapplication.modules.rcumodule.repository.RCUCaseRepository;
import com.example.loanapplication.modules.rcumodule.service.RCUService;

import java.util.UUID;

public class RCUServiceImpl implements RCUService {

    private final RCUCaseRepository rcuCaseRepository;

    public RCUServiceImpl(RCUCaseRepository rcuCaseRepository) {
        this.rcuCaseRepository = rcuCaseRepository;
    }

    @Override
    public RCUCaseResponseDTO CreateRCUCase(UUID loanId) {

        

        return null;
    }
}
