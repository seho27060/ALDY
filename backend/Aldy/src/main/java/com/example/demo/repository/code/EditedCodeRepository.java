package com.example.demo.repository.code;

import com.example.demo.domain.entity.Code.EditedCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EditedCodeRepository extends JpaRepository<EditedCode, Long> {
    List<EditedCode> findBySender_id(long member_id);
    List<EditedCode> findByReceiver_id(long member_id);
    Page<EditedCode> findByReceiver_id(long member_id, Pageable pageable);
    Page<EditedCode> findBySender_id(long member_id, Pageable pageable);


    List<EditedCode> findAllByCode_idAndReceiver_id(Long id, Long id1);

    Long countBySender_id(long member_id);
    Long countByReceiver_id(long member_id);

    Optional<EditedCode> findAllByCode_idAndReceiver_idAndSender_id(Long id, Long id1, Long id2);
}
