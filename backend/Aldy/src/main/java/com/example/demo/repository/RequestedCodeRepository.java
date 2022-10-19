package com.example.demo.repository;

import com.example.demo.domain.entity.RequestedCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestedCodeRepository extends JpaRepository<RequestedCode, Long> {
}
