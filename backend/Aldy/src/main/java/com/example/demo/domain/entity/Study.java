package com.example.demo.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String name;

    private int upperLimit;

    private String introduction;

    private int threshold;

    private int visibility;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Calendar> calendarList;

    @OneToMany(mappedBy = "study",cascade = CascadeType.ALL)
    private List<MemberInStudy> memberList;

}
