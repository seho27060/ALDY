package com.example.demo.repository.code;

import com.example.demo.domain.entity.Code.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findByStudy_idAndWriter_id(long study_id, long member_id);

    Optional<Code> findByStudy_idAndProblem_idAndWriter_idAndProcess(long study_id, long problem_id, long member_id, int level);

    List<Code> findByStudy_idAndProblem_idAndWriter_id(long study_id, long problem_id, Long id);

    List<Code> findByStudy_idAndProblem_id(long study_id, long problem_id);

    Page<Code> findByWriterIdAndProcess(Long member_id, int level, Pageable pageable);

}
