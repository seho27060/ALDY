package com.example.demo.repository;

import com.example.demo.domain.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByStudy_idAndCalendarYearAndCalendarMonth(long study_id, int year, int month);
}
