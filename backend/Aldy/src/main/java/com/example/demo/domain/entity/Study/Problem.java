package com.example.demo.domain.entity.Study;

import com.example.demo.domain.entity.Code.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int problemDay;

    private int problemNum;

    private int problemTier;

    private String problemName;

    private Boolean isChecked;

    private Boolean isLevelChecked;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Code> codeList;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<TagOfProblem> tagOfProblemList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @PrePersist
    public void prePersist() {
        this.isChecked = false;
        this.isLevelChecked = false;
    }

    public void batchCheck() {
        this.isChecked = true;
    }
    public void batchLevelCheck() {
        this.isLevelChecked = true;
    }

}
