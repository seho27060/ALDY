package com.example.demo.repository.study;

import com.example.demo.domain.entity.Study.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudyRepository extends JpaRepository<Study, Long> {

    Page<Study> findAllByNameContaining(String name, Pageable pageable);

}
