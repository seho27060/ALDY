package com.example.demo.domain.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class CalendarDto {
    List<String> days;

}
