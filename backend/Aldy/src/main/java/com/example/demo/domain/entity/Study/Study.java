package com.example.demo.domain.entity.Study;

import com.example.demo.domain.dto.study.CreateStudyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
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

    // 공개 1, 비공개 0
    private int visibility;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Calendar> calendarList;

    @OneToMany(mappedBy = "study",cascade = CascadeType.ALL)
    private List<MemberInStudy> memberList;

    public Study(CreateStudyRequestDto requestDto) {
        this.name = requestDto.getName();
        this.upperLimit = requestDto.getUpperLimit();
        this.introduction = requestDto.getIntroduction();
        this.threshold = requestDto.getThreshold();
        this.visibility = requestDto.getVisibility();
    }
}
