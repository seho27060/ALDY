package com.example.demo.repository.code;

import com.example.demo.domain.entity.Code.RequestedCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestedCodeRepository extends JpaRepository<RequestedCode, Long> {
    List<RequestedCode> findBySender_id(long member_id);

    List<RequestedCode> findByReceiver_id(long member_id);

    List<RequestedCode> findByCodeId(long code_id);

    Page<RequestedCode> findByReceiver_id(long member_id, Pageable pageable);
    Page<RequestedCode> findBySender_id(long member_id, Pageable pageable);

    Long countBySender_idAndIsDone(long member_id, Boolean isdone);
    Long countByReceiver_idAndIsDone(long member_id, Boolean isdone);

    Optional<RequestedCode> findByCode_idAndSender_idAndReceiver_id(long codeId, long senderId, long receiverId);
}
