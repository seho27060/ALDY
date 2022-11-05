package com.example.demo.domain.dto.study;

import com.example.demo.domain.entity.Study.Calendar;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class CalendarDto {

    private Long id;

    private StudyDto study;

    private List<String> days;

    private int calendarMonth;
    private int calendarYear;
    public CalendarDto(Calendar calendar) {
        this.id = calendar.getId();
        this.study = new StudyDto(calendar.getStudy(),0);
        this.calendarMonth = calendar.getCalendarMonth();
        this.calendarYear = calendar.getCalendarYear();
    }
}
