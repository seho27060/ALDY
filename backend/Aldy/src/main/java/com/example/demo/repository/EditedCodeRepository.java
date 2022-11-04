package com.example.demo.repository;

import com.example.demo.domain.entity.EditedCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditedCodeRepository extends JpaRepository<EditedCode, Long> {
    List<EditedCode> findBySender_id(long member_id);

    List<EditedCode> findByReceiver_id(long member_id);

    Long countBySender_id(long member_id);
    Long countByReceiver_id(long member_id);
}
