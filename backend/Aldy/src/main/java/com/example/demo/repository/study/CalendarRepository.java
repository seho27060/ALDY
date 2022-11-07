package com.example.demo.repository.study;

import com.example.demo.domain.entity.Study.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByStudy_idAndCalendarYearAndCalendarMonth(long study_id, int year, int month);

    Optional<Calendar> findByStudy_idAndCalendarMonthAndCalendarYear(Long id, int month, int year);

    List<Calendar> findByStudy_id(long study_id);

}
