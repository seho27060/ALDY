package com.example.demo.repository;

import com.example.demo.domain.entity.RequestedCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestedCodeRepository extends JpaRepository<RequestedCode, Long> {
    List<RequestedCode> findBySender_id(long member_id);

    List<RequestedCode> findByReceiver_id(long member_id);

    Optional<RequestedCode> findByCode_idAndSender_idAndReceiver_id(long codeId, long senderId, long receiverId);
}
