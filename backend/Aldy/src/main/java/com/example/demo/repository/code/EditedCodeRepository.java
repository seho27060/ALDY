package com.example.demo.repository.code;

import com.example.demo.domain.entity.Code.EditedCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditedCodeRepository extends JpaRepository<EditedCode, Long> {
    List<EditedCode> findBySender_id(long member_id);

    List<EditedCode> findByReceiver_id(long member_id);

    List<EditedCode> findAllByCode_idAndReceiver_id(Long id, Long id1);

    Long countBySender_id(long member_id);
    Long countByReceiver_id(long member_id);
}
