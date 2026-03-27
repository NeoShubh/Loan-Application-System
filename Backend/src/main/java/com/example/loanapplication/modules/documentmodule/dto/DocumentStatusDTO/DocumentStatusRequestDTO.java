package com.example.loanapplication.modules.documentmodule.dto.DocumentStatusDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentStatusRequestDTO {

    private String verifiedBy;
    private String documentStatus;
    private String remarks;
}
