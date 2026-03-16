package com.example.loanapplication.modules.loanapplicationmodule.entity;

import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.modules.usermodule.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_stage_history")
public class LoanStageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loanStageHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_Application_id")
    private LoanApplication loanApplicationId; //loan application can have more than one history So to track it we will have this relationship as many to one

    @Enumerated(EnumType.STRING)
    @Column(name = "old_stage")
    private LoanStage oldStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_stage")
    private LoanStage currentStage;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @PrePersist
    public void onCreate() {
        this.changedAt = LocalDateTime.now();
    }

}
