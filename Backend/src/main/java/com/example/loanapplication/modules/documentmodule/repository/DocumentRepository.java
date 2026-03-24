package com.example.loanapplication.modules.documentmodule.repository;

//import com.example.loanapplication.modules.documentmodule.entity.Document;
import com.example.loanapplication.modules.documentmodule.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
