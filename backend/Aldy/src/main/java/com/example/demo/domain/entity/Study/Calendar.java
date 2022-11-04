package com.example.demo.domain.entity.Study;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "study_id")
    private Study study;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL)
    private List<ProblemTable> problemTableList;

    private int calendarMonth;
    private int calendarYear;

}
