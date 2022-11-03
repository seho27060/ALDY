package com.example.demo.repository;

import com.example.demo.domain.entity.RequestedCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestedCodeRepository extends JpaRepository<RequestedCode, Long> {
    List<RequestedCode> findBySender_id(long member_id);

    List<RequestedCode> findByReceiver_id(long member_id);

    Long countBySender_idAndDone(long member_id, Boolean isdone);
    Long countByReceiver_idAndDone(long member_id, Boolean isdone);
}
