package com.example.demo.repository.code;

import com.example.demo.domain.entity.Code.RequestedCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestedCodeRepository extends JpaRepository<RequestedCode, Long> {
    List<RequestedCode> findBySender_id(long member_id);

    List<RequestedCode> findByReceiver_id(long member_id);

    Long countBySender_idAndIsDone(long member_id, Boolean isdone);
    Long countByReceiver_idAndIsDone(long member_id, Boolean isdone);

    Optional<RequestedCode> findByCode_idAndSender_idAndReceiver_id(long codeId, long senderId, long receiverId);
}
