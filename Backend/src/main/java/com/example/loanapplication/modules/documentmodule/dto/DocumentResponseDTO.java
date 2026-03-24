package com.example.loanapplication.modules.documentmodule.dto;


import com.example.loanapplication.modules.documentmodule.enums.DocumentStatus;
import com.example.loanapplication.modules.documentmodule.enums.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDTO {
    private UUID DocumentId;
    private UUID loanApplication;
    private UUID applicant;
    private DocumentStatus documentStatus;
    private DocumentType documentType;
    private String fileUrl;
    private UUID uploadedBy;
    private UUID verifiedBy;
    private String remarks;
}
