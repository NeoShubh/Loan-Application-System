package com.example.loanapplication.modules.rcumodule.entity;

import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.modules.rcumodule.enums.RCUStatus;
import com.example.loanapplication.modules.usermodule.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rcuCase")
public class RCUCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID rcuCaseId;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanApplication loan;

    @Enumerated(EnumType.STRING)
    private RCUStatus rcuStatus;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
