package com.example.demo.repository;

import com.example.demo.domain.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudyRepository extends JpaRepository<Study, Long> {

    Page<Study> findAllByNameContaining(String name, Pageable pageable);

}
