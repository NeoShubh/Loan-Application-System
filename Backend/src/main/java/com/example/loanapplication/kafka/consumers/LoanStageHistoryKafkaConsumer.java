package com.example.loanapplication.kafka.consumers;

import com.example.loanapplication.kafka.events.LoanStageChangedEvent;
import com.example.loanapplication.kafka.events.LoanStageHistoryEvent;
import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.loanapplicationmodule.service.LoanApplicationService;
import com.example.loanapplication.modules.rcumodule.service.RCUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoanStageHistoryKafkaConsumer {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @KafkaListener(topics = "loan-stage-history-events", groupId = "loan-stage-history-group")
    public void consume(LoanStageHistoryEvent event) {

        System.out.println("Received event: " + event);

        loanApplicationService.createLoanStageHistory(event.getLoanID(), event.getUserID(), event.getLoanStageHistoryRequestDTO());
    }
}
